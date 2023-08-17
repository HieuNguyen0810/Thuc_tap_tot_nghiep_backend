package com.his.constant;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    SUCCESS(200, "Success"),
    PASSWORD_RESET(200_1, "Your password has been reset, please check your email to get new password!"),

    EMAIL_NOT_EXISTS(400_1, "Email is not exists!"),


    LOGIN_FAILED(401_1, "Email or password is incorrect!"),
    PASSWORD_IS_INCORRECT(401_2, "Password is incorrect!"),

    JWT_TOKEN_EXPIRED(401_2, "JWT Token expired!"),


    USER_NOT_FOUND(404_1, "User not found"),
    TARGET_NOT_FOUND(404_1, "Target not found"),


    FORBIDDEN(403_1, "You don't have permission");

    private final int code;
    private final String message;

     ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
