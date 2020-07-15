package com.maciek.socialnetworkingsite.rest.dto.mapper;

import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.rest.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTOMapper {

    private UserDTOMapper() {
    }

    public static List<UserDTO> mapToUserDTOs(List<User> users) {
        return users.stream()
                .map(user -> mapToUserDTO(user))
                .collect(Collectors.toList());

    }

    public static UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .build();
    }

}
