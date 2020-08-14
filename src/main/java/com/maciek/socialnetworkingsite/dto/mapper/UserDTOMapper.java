package com.maciek.socialnetworkingsite.dto.mapper;

import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTOMapper {

    private UserDTOMapper() {
    }

    public static List<UserDTO> mapUsersToDTOs(List<User> users) {
        return users.stream()
                .map(user -> mapUserToDTO(user))
                .collect(Collectors.toList());

    }

    public static UserDTO mapUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .build();
    }

    public static User mapDTOtoUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .password(userDTO.getPassword())
                .build();
    }

}
