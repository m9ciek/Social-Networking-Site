package com.maciek.socialnetworkingsite.service;

import com.maciek.socialnetworkingsite.dao.UserRepository;
import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public User registerNewUser(UserDTO accountDTO) throws EmailExistsException {
        Optional<User> databaseUser = userRepository.findByEmail(accountDTO.getEmail());
        if(databaseUser.isPresent()){
            throw new EmailExistsException("User with email:" + databaseUser.get().getEmail() +" already exists.");
        }

        User user = new User();
        user.setFirstName(accountDTO.getFirstName());
        user.setLastName(accountDTO.getLastName());
        user.setEmail(accountDTO.getEmail());
        user.setPassword(passwordEncoder.encode(accountDTO.getPassword()));

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User login(UserDTO userDTO) {
        Optional<User> userFromDb = userRepository.findByEmail(userDTO.getEmail());

        if(userFromDb.isEmpty() || wrongPassword(userFromDb.get(), userDTO)){
            throw new RuntimeException("User not found or wrong Password");
        }
        return userFromDb.get();
    }

    boolean wrongPassword(User userFromDb, UserDTO userDTO) {
        return !passwordEncoder.matches(userDTO.getPassword(), userFromDb.getPassword());
    }
}
