package com.maciek.socialnetworkingsite.service;


import com.maciek.socialnetworkingsite.rest.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.UserExistsException;

import java.util.List;

public interface UserService {
    User registerNewUser(UserDTO accountDTO) throws UserExistsException;
    List<User> getUsers(int page);
    User getUserById(long id);
    List<User> getUsersWithPosts(int page);
}
