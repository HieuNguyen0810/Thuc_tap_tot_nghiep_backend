package com.his.exceptionhandler;

import com.his.constant.ErrorMessage;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiException extends Exception{
    private final ErrorMessage errorMessage;
}

