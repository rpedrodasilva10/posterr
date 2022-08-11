package com.posterr.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(BusinessException ex) {
        return new ResponseEntity<>(ex.getApiErrorResponse(),
                HttpStatus.valueOf(ex.getApiErrorResponse().getCode()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException typeMismatchException) {
        String errorDescription = String
                .format("'%s' is an invalid value for the parameter '%s' (%s)", typeMismatchException.getValue(),
                        typeMismatchException.getName(), typeMismatchException.getRequiredType());
        BusinessException businessException = new BusinessException(
                HttpStatus.BAD_REQUEST.value(),
                errorDescription, String.format("Invalid value for parameter '%s'", typeMismatchException.getName()), typeMismatchException, new ArrayList<>());
        return new ResponseEntity<>(businessException.getApiErrorResponse(),
                HttpStatus.valueOf(businessException.getCode()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<ApiError> errors = new ArrayList<>();
        result.getFieldErrors().parallelStream().forEach(fieldError ->
                errors.add(new ApiError(fieldError.getCode(), fieldError.getDefaultMessage())));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiErrorResponse.builder()
                                .code(-1)
                                .message("Invalid request body!")
                                .errors(errors)
                                .description("Payload validation failed! Check the list of errors for more information").build()


                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        BusinessException businessException = new BusinessException(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(), ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(businessException.getApiErrorResponse(),
                HttpStatus.valueOf(businessException.getCode()));
    }
}
