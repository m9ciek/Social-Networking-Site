package com.maciek.socialnetworkingsite.service;

import com.maciek.socialnetworkingsite.dao.UserRepository;
import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.EmailAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private UserRepository userRepository;

    @Autowired
    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerNewUser(UserDTO accountDTO){
        Optional<User> databaseUser = userRepository.findByEmail(accountDTO.getEmail());
        if(databaseUser.isPresent()){
            throw new EmailAlreadyExistsException("User with email:" + databaseUser.get().getEmail() +" already exists.");
        }

        User user = new User();
        user.setFirstName(accountDTO.getFirstName());
        user.setLastName(accountDTO.getLastName());
        user.setEmail(accountDTO.getEmail());
        user.setPassword(accountDTO.getPassword());

        return userRepository.save(user);
    }
}
