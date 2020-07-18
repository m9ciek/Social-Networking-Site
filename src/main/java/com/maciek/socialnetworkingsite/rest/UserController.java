package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.rest.dto.UserDTO;
import com.maciek.socialnetworkingsite.rest.dto.mapper.UserDTOMapper;
import com.maciek.socialnetworkingsite.security.LoginDetailsService;
import com.maciek.socialnetworkingsite.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final LoginDetailsService loginDetailsService;

    @Autowired
    public UserController(UserService userService, LoginDetailsService loginDetailsService) {
        this.userService = userService;
        this.loginDetailsService = loginDetailsService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(required = false) Integer page) {
        int pageNumber = page != null && page > 0 ? page : 0;
        return ResponseEntity.ok(UserDTOMapper.mapUsersToDTOs(userService.getUsers(pageNumber)));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(UserDTOMapper.mapUserToDTO(userService.getUserById(id)));
    }

    @GetMapping("/users/posts")
    public ResponseEntity<List<User>> getUsersWithPosts(@RequestParam(required = false) Integer page) {
        int pageNumber = page != null && page > 0 ? page : 0;
        return ResponseEntity.ok(userService.getUsersWithPosts(pageNumber));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userService.registerUser(UserDTOMapper.mapDTOtoUser(userDTO)), HttpStatus.CREATED);
    }

    @DeleteMapping("/users")
    public ResponseEntity deleteUser() {
        long userId = loginDetailsService.getLoggedUser().getId();
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
