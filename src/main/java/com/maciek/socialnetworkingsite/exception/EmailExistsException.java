package com.maciek.socialnetworkingsite.exception;


public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String message) {
        super(message);
    }
}
