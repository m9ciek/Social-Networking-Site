package com.maciek.socialnetworkingsite.service;

import com.maciek.socialnetworkingsite.dao.UserRepository;
import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.EmailExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class DefaultUserServiceTest {

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    PasswordEncoder passwordEncoderMock;

    @Mock
    DefaultUserService userServiceMock;

    @InjectMocks
    DefaultUserService userService;


    @Test
    void shouldMapDtoToEntity(){
        //given
        User user = new User(1L,"Foo","Bar", "foo@bar.com","qwe123");
        UserDTO userDTO = new UserDTO(1L,"Foo","Bar", "foo@bar.com","password");

        //when
        when(passwordEncoderMock.encode(anyString())).thenReturn("qwe123");
        //then - compare each field except id
        assertTrue(new ReflectionEquals(user,"id").matches(userService.mapDtoToUser(userDTO)));
    }

    @Test
    void shouldReturnRegisteredUser(){
        //given
        User user = new User(1L,"Foo","Bar", "foo@bar.com","password");
        UserDTO userDtoToRegister = new UserDTO(1L,"Foo","Bar", "foo@bar.com","password");

        //when
        when(userServiceMock.mapDtoToUser(any(UserDTO.class))).thenReturn(user);
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);

        //then
        assertEquals(user,userService.registerNewUser(userDtoToRegister));
    }

    @Test
    void shouldThrowExceptionThatUserEmailExists() {
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("foo@bar.com");

        //when
        when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        //then
        assertThrows(EmailExistsException.class, () -> userService.registerNewUser(userDTO));
    }

    @Test
    void shouldReturnAllUsers(){
        //given
        User user1 = new User(1L,"Foo","Bar", "foo@bar.com","password");
        User user2 = new User(2L,"Bar","Foo", "bar@foo.com","password");
        List<User> userList = Arrays.asList(user1,user2);

        //when
        when(userRepositoryMock.findAll()).thenReturn(userList);

        //then
        assertEquals(userList, userService.getAllUsers());
    }

    @Test
    void shouldReturnTrueIfPasswordIsWrong(){
        //given
        UserDTO userDtoToCheck = new UserDTO();
        User userFromDb = new User();
        userDtoToCheck.setPassword("foobar");
        userFromDb.setPassword("barfoo");

        //when

        //then
        assertTrue(userService.wrongPassword(userFromDb,userDtoToCheck));
    }


}