package com.posterr.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiError {
    private final String code;
    private final String message;
    private String nativeMessage;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
