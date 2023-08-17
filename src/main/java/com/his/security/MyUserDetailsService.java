package com.his.security;


import com.his.entity.CustomUserDetails;
import com.his.entity.Customer;
import com.his.entity.User;
import com.his.repository.CustomerRepo;
import com.his.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    private final CustomerRepo customerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepo.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Email " + username + " not found!"));
//        return new CustomUserDetails(user);

        Optional<User> optionalUser = userRepo.findByUsername(username);
        if (optionalUser.isPresent()) {
            return new CustomUserDetails(optionalUser.get());
        }

        System.out.println("customer ===========================########");
        Optional<Customer> optionalCustomer = customerRepo.findByEmail(username);
        return optionalCustomer.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + username + " not found!"));
    }
}

