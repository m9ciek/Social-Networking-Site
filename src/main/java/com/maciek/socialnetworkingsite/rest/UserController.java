package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.EmailExistsException;
import com.maciek.socialnetworkingsite.exception.response.UserErrorResponse;
import com.maciek.socialnetworkingsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName()); //getting current logged user
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody @Valid UserDTO userDTO){
        return ResponseEntity.ok(userService.registerNewUser(userDTO));
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleUserEmailException(EmailExistsException e){
        UserErrorResponse response = new UserErrorResponse();

        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.setMessage(e.getMessage());
        response.setDate(new Date());

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
