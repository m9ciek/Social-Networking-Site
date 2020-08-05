package com.maciek.socialnetworkingsite.unit.mapper;

import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.rest.dto.UserDTO;
import com.maciek.socialnetworkingsite.rest.dto.mapper.UserDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOMapperTest {

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        this.user = User.builder().id(1L).firstName("John").lastName("Doe")
                .email("john@doe.com").password("joe123")
                .build();
        this.userDTO = UserDTO.builder().id(1L).firstName("John").lastName("Doe")
                .email("john@doe.com").password("joe123")
                .build();
    }


    @Test
    void shouldMapUserToDTO() {
        UserDTO mappedUserDTO = UserDTOMapper.mapUserToDTO(this.user);
        assertAll(
                () -> assertEquals(this.userDTO.getId(), mappedUserDTO.getId()),
                () -> assertEquals(this.userDTO.getFirstName(), mappedUserDTO.getFirstName()),
                () -> assertEquals(this.userDTO.getLastName(), mappedUserDTO.getLastName()),
                () -> assertEquals(this.userDTO.getEmail(), mappedUserDTO.getEmail()),
                () -> assertEquals(this.userDTO.getPassword(), mappedUserDTO.getPassword())
        );

    }

    @Test
    void shouldMapDTOtoUser() {
        User mappedUser = UserDTOMapper.mapDTOtoUser(this.userDTO);
        assertAll(
                () -> assertEquals(this.user.getId(), mappedUser.getId()),
                () -> assertEquals(this.user.getFirstName(), mappedUser.getFirstName()),
                () -> assertEquals(this.user.getLastName(), mappedUser.getLastName()),
                () -> assertEquals(this.user.getEmail(), mappedUser.getEmail()),
                () -> assertEquals(this.user.getPassword(), mappedUser.getPassword())
        );
    }

    @Test
    void mapUsersToDTOs() {
        User newUser = User.builder().id(2L).firstName("Doe").lastName("John")
                .email("doe@john.com").password("123doe123")
                .build();

        UserDTO newUserDTO = UserDTO.builder().id(2L).firstName("Doe").lastName("John")
                .email("doe@john.com").password("123doe123")
                .build();

        List<User> usersList = Arrays.asList(this.user, newUser);
        List<UserDTO> usersDTOList = Arrays.asList(this.userDTO, newUserDTO);

        assertEquals(usersDTOList.size(), UserDTOMapper.mapUsersToDTOs(usersList).size());
        assertEquals(usersDTOList.get(0).getId(), UserDTOMapper.mapUsersToDTOs(usersList).get(0).getId());

    }
}