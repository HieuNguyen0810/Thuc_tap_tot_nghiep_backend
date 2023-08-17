package com.his.dto.auth;

import com.his.entity.Customer;
import com.his.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String accessToken;
    private String refreshToken;
    private String username;
    private User user;
    private Customer customer;
    private int code;
}
