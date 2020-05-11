package com.maciek.socialnetworkingsite.service;

import com.maciek.socialnetworkingsite.repository.UserRepository;
import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            throw new EmailExistsException("User with email: " + databaseUser.get().getEmail() +" already exists.");
        }
        User user = mapDtoToUser(accountDTO);

        return userRepository.save(user);
    }

    public User mapDtoToUser(UserDTO accountDTO) {
        User user = new User();
        user.setFirstName(accountDTO.getFirstName());
        user.setLastName(accountDTO.getLastName());
        user.setEmail(accountDTO.getEmail());
        user.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        return user;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User getUserById(long id){
        Optional<User> userFromDatabase = userRepository.findById(id);
        if(userFromDatabase.isPresent()){
            return userFromDatabase.get();
        }else {
            throw new UsernameNotFoundException("User with id: " + id + " has not been found in database.");
        }
    }
}
