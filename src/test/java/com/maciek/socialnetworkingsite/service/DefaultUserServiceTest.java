package com.maciek.socialnetworkingsite.service;

import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.EmailExistsException;
import com.maciek.socialnetworkingsite.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp(){
        this.user = new User(1L,"Foo","Bar", "foo@bar.com","qwe123", null);
        this.userDTO = new UserDTO(1L,"Foo","Bar", "foo@bar.com","password");
    }

    @Test
    void shouldThrowExceptionThatUserEmailExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("foo@bar.com");

        when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(EmailExistsException.class, () -> userService.registerNewUser(userDTO));
    }

    @Test
    void shouldReturnAllUsers(){
        User user1 = new User(1L,"Foo","Bar", "foo@bar.com","password", null);
        User user2 = new User(2L,"Bar","Foo", "bar@foo.com","password", null);
        List<User> userList = Arrays.asList(user1,user2);

        when(userRepositoryMock.findAll()).thenReturn(userList);

        assertEquals(userList, userService.getAllUsers());
    }

}