package com.his.api;

import com.his.constant.ErrorMessage;
import com.his.dto.auth.*;
import com.his.dto.response.ApiResponse;
import com.his.entity.Customer;
import com.his.exceptionhandler.ApiException;
import com.his.repository.RoleRepo;
import com.his.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RoleRepo roleRepo;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws Exception {
        LoginResponse loginResponse = authenticationService.login(request);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Customer customer) throws Exception {
        Customer signedUpCustomer = authenticationService.signup(customer);
        if (signedUpCustomer != null) {
            return ResponseEntity.ok(new SignupResponse("Signup successfully!", 200));
        }
        return (ResponseEntity<?>) ResponseEntity.badRequest();
    }

    @PostMapping("/reset-password")
    public ApiResponse<?> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) throws ApiException {
        authenticationService.resetPassword(resetPasswordDto);
        return new ApiResponse<>(ErrorMessage.PASSWORD_RESET, null);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {

        return null;
    }

    @GetMapping("/logout")
    public ApiResponse<?> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        authenticationService.logout(token);
        return new ApiResponse<>(ErrorMessage.SUCCESS, "Logged out!!!");
    }

    @GetMapping("/getAllRoles")
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleRepo.findAll());
    }
}

