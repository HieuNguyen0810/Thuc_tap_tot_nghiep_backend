package com.his.dto.response;

import com.his.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    public String message;
    public int code;
    public T data;

    public ApiResponse(ErrorMessage errorMessage) {
        this.code = errorMessage.getCode();
        this.message = errorMessage.getMessage();
    }

    public ApiResponse(ErrorMessage errorMessage, T data) {
        this.code = errorMessage.getCode();
        this.message = errorMessage.getMessage();
        this.data = data;
    }

}
