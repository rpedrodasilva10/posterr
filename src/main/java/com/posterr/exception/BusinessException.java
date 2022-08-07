package com.posterr.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends Exception {

    private static final long serialVersionUID = -4315650330185909419L;
    private final Integer code;
    private final String message;
    private final String nativeMessage;
    private final ApiErrorResponse apiErrorResponse;

    public BusinessException(Integer code, String message, String nativeMessage, Exception ex, List<ApiError> errorsList) {
        super(nativeMessage, ex);
        this.code = code;
        this.message = message;
        this.nativeMessage = nativeMessage;
        this.apiErrorResponse = new ApiErrorResponse(code, message, nativeMessage, errorsList);

        ex.printStackTrace();
    }

    public BusinessException(Integer code, String message, String nativeMessage, Exception ex) {
        super(nativeMessage, ex);
        this.code = code;
        this.message = message;
        this.nativeMessage = nativeMessage;
        this.apiErrorResponse = new ApiErrorResponse(code, message, nativeMessage, new ArrayList<>());

        ex.printStackTrace();
    }

    public BusinessException(Integer code, String message, String nativeMessage) {
        super(nativeMessage);
        this.code = code;
        this.message = message;
        this.nativeMessage = nativeMessage;
        this.apiErrorResponse = new ApiErrorResponse(code, message, nativeMessage, new ArrayList<>());

    }
}
