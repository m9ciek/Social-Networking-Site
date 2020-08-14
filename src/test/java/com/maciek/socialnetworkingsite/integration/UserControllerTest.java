package com.maciek.socialnetworkingsite.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.rest.UserController;
import com.maciek.socialnetworkingsite.security.LoginDetailsService;
import com.maciek.socialnetworkingsite.service.user.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class UserControllerTest {

    @Mock
    private UserService userServiceMock;

    @Mock
    private LoginDetailsService loginDetailsServiceMock;

    private MockMvc mockMvc;
    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        UserController userController = new UserController(userServiceMock, loginDetailsServiceMock);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        this.userDTO = UserDTO.builder().id(1L).firstName("foo")
                .lastName("bar").email("foo@bar.com").password("password").build();

        this.user = new User(1L, "foo", "bar", "foo@bar.com", "password", null, null);
    }

    @Test
    void shouldReturnOkAndJSONWhenFindAllUsers() throws Exception {
        User user1 = new User(1L, "Foo", "Bar", "foo@bar.com", "password", null, null);
        User user2 = new User(2L, "Bar", "Foo", "bar@foo.com", "password", null, null);
        List<User> userList = Arrays.asList(user1, user2);

        when(userServiceMock.getUsers(0)).thenReturn(userList); // page 0 - default

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", Matchers.hasSize(2))); // $ - list objects

        verify(userServiceMock).getUsers(0);
    }

    @Test
    void shouldReturnOkWhenRegisteringValidUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonUser = mapper.writeValueAsString(userDTO);

        when(userServiceMock.registerUser(user)).thenReturn(new User());

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonUser)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
//                .andExpect(jsonPath("$.email").exists());

        verify(userServiceMock).registerUser(any(User.class));
    }
}