package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.security.LoginDetailsService;
import com.maciek.socialnetworkingsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private UserService userService;
    private LoginDetailsService loginDetailsService;

    @Autowired
    public UserController(UserService userService, LoginDetailsService loginDetailsService) {
        this.userService = userService;
        this.loginDetailsService = loginDetailsService;
    }

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

}
