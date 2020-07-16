package com.maciek.socialnetworkingsite.service.user;


import com.maciek.socialnetworkingsite.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    List<User> getUsers(int page);
    User getUserById(long id);
    List<User> getUsersWithPosts(int page);
    void deleteUser(long id);
}
