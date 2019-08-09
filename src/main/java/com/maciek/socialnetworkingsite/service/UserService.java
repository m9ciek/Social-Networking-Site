package com.maciek.socialnetworkingsite.service;


import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;

public interface UserService {
    User registerNewUser(UserDTO accountDTO);
}
