package com.wcc.distance.exception;

public class DuplicatePostCodeException extends RuntimeException {

    public DuplicatePostCodeException(String postCode) {
        super(String.format("More than 1 coordinates are found for post code: %s", postCode));
    }
}
