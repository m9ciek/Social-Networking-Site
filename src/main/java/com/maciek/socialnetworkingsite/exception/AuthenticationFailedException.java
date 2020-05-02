package com.maciek.socialnetworkingsite.exception;

public class AuthenticationFailedException extends RuntimeException {

    public AuthenticationFailedException() {
    }

    public AuthenticationFailedException(String message) {
        super(message);
    }
}
