package com.his.service.impl;

import com.his.constant.ErrorMessage;
import com.his.dto.auth.ResetPasswordDto;
import com.his.entity.CustomUserDetails;
import com.his.entity.Customer;
import com.his.exceptionhandler.ApiException;
import com.his.security.MyUserDetailsService;
import com.his.dto.auth.LoginRequest;
import com.his.dto.auth.LoginResponse;
import com.his.entity.Role;
import com.his.repository.CustomerRepo;
import com.his.repository.RoleRepo;
import com.his.service.AuthenticationService;
import com.his.security.JwtTokenProvider;
import com.his.service.CustomerService;
import com.his.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomerRepo customerRepo;
    private final CustomerService customerService;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final MyUserDetailsService userDetailsService;
    private final EmailService emailService;

    @Override
    public Customer signup(Customer customer) throws Exception {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new Exception("Email already exists!");
        }
        Customer signedUpCustomer = new Customer();
        signedUpCustomer.setEmail(customer.getEmail());
        signedUpCustomer.setPassword(passwordEncoder.encode(customer.getPassword()));
        signedUpCustomer.setFullName(customer.getFullName());
        signedUpCustomer.setPhone(customer.getPhone());
        signedUpCustomer.setDob(customer.getDob());
        signedUpCustomer.setStatus(true);
        signedUpCustomer.setContactPerson(customer.getContactPerson());
        signedUpCustomer.setTarget(customer.getTarget());

        Role role = roleRepo.getByName("ROLE_CUSTOMER");

        signedUpCustomer.setRole(role);

        customerService.create(signedUpCustomer);

        return signedUpCustomer;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            throw new ApiException(ErrorMessage.LOGIN_FAILED);
        }

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(request.getEmail());
        if (userDetails == null) {
            return LoginResponse.builder()
                    .message("Some errors occurs!")
                    .build();
        }

        String jwtToken = jwtTokenProvider.generateAccessToken(userDetails);
        return LoginResponse.builder()
                .message("Login successfully!")
                .accessToken(jwtToken)
                .username(userDetails.getUsername())
                .user(userDetails.getUser())
                .customer(userDetails.getCustomer())
                .code(200)
                .build();
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto) throws ApiException {
        Customer customer = customerRepo.findByEmail(resetPasswordDto.getEmail())
                .orElseThrow(() -> new ApiException(ErrorMessage.EMAIL_NOT_EXISTS));

        String newPassword = UUID.randomUUID().toString().substring(24);
        emailService.sendSimpleMessage(resetPasswordDto.getEmail(), "RESET PASSWORD", "Mật khẩu mới của bạn là: " + newPassword);

        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepo.save(customer);
    }

    @Override
    public void logout(String token) {
        jwtTokenProvider.logout(token);
    }
}
