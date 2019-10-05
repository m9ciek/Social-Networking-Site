package com.maciek.socialnetworkingsite.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
    }
    public EmailNotFoundException(String message) {
        super(message);
    }
}
