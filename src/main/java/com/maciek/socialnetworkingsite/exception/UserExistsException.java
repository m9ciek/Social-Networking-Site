package com.maciek.socialnetworkingsite.exception;


public class UserExistsException extends RuntimeException {

    public UserExistsException(String message) {
        super(message);
    }
}
