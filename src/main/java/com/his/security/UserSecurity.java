package com.his.security;

import com.his.dto.UserDto;
import com.his.entity.Customer;
import com.his.exceptionhandler.ApiException;
import com.his.service.CustomerService;
import com.his.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("UserSecurity")
@RequiredArgsConstructor
public class UserSecurity {
    private final UserService userService;
    private final CustomerService customerService;

    public boolean hasUserId(Authentication authentication, Long userid) throws ApiException {
        String currentUsername = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return true;
        } else {
            UserDto user = userService.getById(userid);
            return currentUsername.equals(user.getEmail());
        }
    }
}
