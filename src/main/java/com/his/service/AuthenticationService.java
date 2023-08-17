package com.his.service;

import com.his.dto.auth.LoginRequest;
import com.his.dto.auth.LoginResponse;
import com.his.dto.auth.ResetPasswordDto;
import com.his.dto.auth.SignupRequest;
import com.his.entity.Customer;
import com.his.entity.User;
import com.his.exceptionhandler.ApiException;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    Customer signup(Customer customer) throws Exception;

    LoginResponse login(LoginRequest request) throws Exception;

    void resetPassword(ResetPasswordDto resetPasswordDto) throws ApiException;

    void logout(String token);
}

