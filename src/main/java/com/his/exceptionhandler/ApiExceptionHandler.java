package com.his.exceptionhandler;

import com.his.constant.ErrorMessage;
import com.his.dto.response.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ApiResponse<?> handleExpiredJwtException(ExpiredJwtException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", new Date());
        errorDetails.put("status", HttpStatus.UNAUTHORIZED.value());
        errorDetails.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        errorDetails.put("message", "JWT expired");

        return new ApiResponse<>(ErrorMessage.JWT_TOKEN_EXPIRED, errorDetails);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleException(Exception ex, WebRequest webRequest) {
        return ApiResponse.builder()
                .message(ex.getMessage())
                .build();
    }

    // === handle validation fields Exception === //
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> handleValidationException(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return ApiResponse.builder()
//                .code(400)
//                .message("Some fields are invalid!")
//                .build();
        ObjectError error = ex.getBindingResult().getAllErrors().get(0);
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();

        return ApiResponse.builder()
                .code(400)
                .message(fieldName + ": " + errorMessage)
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorMessages = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return ApiResponse.builder()
                .message(errorMessages.get(0))
                .code(400)
                .build();
    }

    // === handle API Exception === //
    @ExceptionHandler(ApiException.class)
    public ApiResponse<Object> handleApiException(ApiException ex) {
        return ApiResponse.builder()
                .code(ex.getErrorMessage().getCode())
                .message(ex.getErrorMessage().getMessage())
                .build();
    }
}

