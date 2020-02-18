package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.EmailExistsException;
import com.maciek.socialnetworkingsite.exception.response.UserErrorResponse;
import com.maciek.socialnetworkingsite.security.LoginDetailsService;
import com.maciek.socialnetworkingsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;
    private LoginDetailsService loginDetailsService;

    @Autowired
    public UserController(UserService userService, LoginDetailsService loginDetailsService) {
        this.userService = userService;
        this.loginDetailsService = loginDetailsService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid UserDTO userDTO){
        return new ResponseEntity<>(userService.registerNewUser(userDTO), HttpStatus.CREATED);
    }

    @GetMapping("/main")
    public String welcomeUser(){
        User loggedUser = loginDetailsService.getLoggedUser();
        return "Welcome " + loggedUser.getFirstName() + " " + loggedUser.getLastName()+ "!";
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
