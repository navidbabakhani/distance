package com.wcc.distance.controller;

import com.wcc.distance.exception.DuplicatePostCodeException;
import com.wcc.distance.exception.PostCodeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Value("${spring.application.name}")
    private String serviceName;
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(Throwable t) {
        logger.error("Unknown Exception", t);
        return createResponseEntityWithStatus(INTERNAL_SERVER_ERROR, t.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleThrowable(IOException ex) {
        logger.error("IOException", ex);
        return createResponseEntityWithStatus(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(PostCodeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWebExchangeBindException(PostCodeNotFoundException ex) {
        logger.error("PostCodeNotFoundException", ex);
        return createResponseEntityWithStatus(NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicatePostCodeException.class)
    public ResponseEntity<ErrorResponse> handleWebExchangeBindException(DuplicatePostCodeException ex) {
        logger.error("DuplicatePostCodeException", ex);
        return createResponseEntityWithStatus(BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, ServerWebInputException.class})
    public ResponseEntity<ErrorResponse> handleServerWebInputException(Exception ex) {
        logger.error("Invalid request", ex);
        return createResponseEntityWithStatus(BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleServerWebInputException(HttpMessageNotReadableException ex) {
        logger.error("Invalid request", ex);
        return createResponseEntityWithStatus(BAD_REQUEST, "You need to provide correct coordinates in request body");
    }

    private ResponseEntity<ErrorResponse> createResponseEntityWithStatus(HttpStatus status, String msg) {
        return new ResponseEntity<>(new ErrorResponse(status.value(), msg), status);
    }

    class ErrorResponse {
        String service;
        int errorCode;
        String errorMessage;

        public ErrorResponse(int errorCode, String errorMessage) {
            service = serviceName;
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        public String getService() {
            return service;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
