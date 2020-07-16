package com.maciek.socialnetworkingsite.exception.advice;

import com.maciek.socialnetworkingsite.exception.PostNotFoundException;
import com.maciek.socialnetworkingsite.exception.response.PostErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class PostControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<PostErrorResponse> handlePostNotFoundException(PostNotFoundException e){
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(e.getMessage());

        PostErrorResponse response = new PostErrorResponse(404, errorMessages, LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
