package com.maciek.socialnetworkingsite.dto.mapper;

import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserDtoMapper() {
    }

    public static User mapDtoToUser(UserDTO accountDTO, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setFirstName(accountDTO.getFirstName());
        user.setLastName(accountDTO.getLastName());
        user.setEmail(accountDTO.getEmail());
        user.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        return user;
    }
}
