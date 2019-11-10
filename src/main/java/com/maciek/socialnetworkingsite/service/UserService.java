package com.maciek.socialnetworkingsite.service;


import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.EmailExistsException;

import java.util.List;

public interface UserService {
    User registerNewUser(UserDTO accountDTO) throws EmailExistsException;
    List<User> getAllUsers();
    User login(UserDTO userDTO);
}
