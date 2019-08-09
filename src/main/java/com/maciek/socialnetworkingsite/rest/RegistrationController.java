package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.exception.EmailAlreadyExistsException;
import com.maciek.socialnetworkingsite.exception.UserErrorResponse;
import com.maciek.socialnetworkingsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody @Valid UserDTO userDTO, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body("At least one field is empty");
        }else {
            return ResponseEntity.ok(userService.registerNewUser(userDTO));
        }

    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleUserEmailException(EmailAlreadyExistsException e){
        UserErrorResponse response = new UserErrorResponse();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(e.getMessage());
        response.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
