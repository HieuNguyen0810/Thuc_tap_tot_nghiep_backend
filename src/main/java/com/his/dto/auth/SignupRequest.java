package com.his.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {
    private String email;
    private String password;
    private String name;
    private String fullName;
    private String phone;
    private Integer roleId;
}
