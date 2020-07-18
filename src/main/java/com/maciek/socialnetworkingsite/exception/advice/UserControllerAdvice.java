package com.maciek.socialnetworkingsite.exception.advice;

import com.maciek.socialnetworkingsite.exception.AuthenticationFailedException;
import com.maciek.socialnetworkingsite.exception.UserExistsException;
import com.maciek.socialnetworkingsite.exception.response.UserErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class UserControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleUserEmailException(UserExistsException e) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(e.getMessage());

        UserErrorResponse response = new UserErrorResponse();
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.setErrors(errorMessages);
        response.setDate(new Date());

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(e.getMessage());

        UserErrorResponse response = new UserErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setErrors(errorMessages);
        response.setDate(new Date());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleAuthenticationFailedException(AuthenticationFailedException e) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(e.getMessage());

        UserErrorResponse response = new UserErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setErrors(errorMessages);
        response.setDate(new Date());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errorMessages.add(error.getDefaultMessage());
        }
        UserErrorResponse response = new UserErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setErrors(errorMessages);
        response.setDate(new Date());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
