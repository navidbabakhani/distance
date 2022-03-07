package com.wcc.distance.exception;

public class PostCodeNotFoundException extends RuntimeException {

    public PostCodeNotFoundException(String postCode) {
        super(String.format("Post Code is not found: %s", postCode));
    }
}
