package com.maciek.socialnetworkingsite.service;

import com.maciek.socialnetworkingsite.dao.UserRepository;
import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.EmailExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    PasswordEncoder passwordEncoderMock;

    @InjectMocks
    DefaultUserService defaultUserService;

    @Test
    void shouldThrowExceptionWhenEmailExistsDuringRegistration(){
        Optional<User> userFromDb = Optional.of(new User(1L, "John", "Doe", "john@doe.com", "johndoepassword"));
        Mockito.when(userRepositoryMock.findByEmail("john@doe.com")).thenReturn(userFromDb);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("john@doe.com");

        //lambda because Executable is a functional interface
        Assertions.assertThrows(EmailExistsException.class, () -> defaultUserService.registerNewUser(userDTO));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound(){
        Mockito.when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.empty());
        UserDTO userDTO = new UserDTO();

        Assertions.assertThrows(RuntimeException.class, ()-> defaultUserService.login(userDTO));
    }

    @Test
    void shouldReturnTrueWhenWrongPassword(){
        User userFromDb = new User();
        userFromDb.setPassword("12345");

        UserDTO userToLogin = new UserDTO();
        userToLogin.setPassword("qwe123");
        Mockito.when(passwordEncoderMock.matches(anyString(),anyString())).thenReturn(false);
        Assertions.assertTrue(defaultUserService.wrongPassword(userFromDb,userToLogin));
    }
}