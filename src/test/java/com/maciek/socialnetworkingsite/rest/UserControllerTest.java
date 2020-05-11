package com.maciek.socialnetworkingsite.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maciek.socialnetworkingsite.dto.UserDTO;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.EmailExistsException;
import com.maciek.socialnetworkingsite.exception.UserErrorResponse;
import com.maciek.socialnetworkingsite.security.LoginDetailsService;
import com.maciek.socialnetworkingsite.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
    private  User user;

    @BeforeEach
    void setUp() {
        UserController userController = new UserController(userServiceMock, loginDetailsServiceMock);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        this.userDTO = new UserDTO(1L, "foo", "bar", "foo@bar.com","password");
        this.user = new User(1L, "foo", "bar", "foo@bar.com","password", null);
    }

    @Test
    void shouldReturnOkAndJSONWhenFindAllUsers() throws Exception {
        User user1 = new User(1L,"Foo","Bar", "foo@bar.com","password", null);
        User user2 = new User(2L,"Bar","Foo", "bar@foo.com","password", null);
        List<User> userList = Arrays.asList(user1,user2);

        when(userServiceMock.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$" , Matchers.hasSize(2))); // $ - list objects

        verify(userServiceMock).getAllUsers();
    }

    @Test
    void shouldReturnOkWhenRegisteringValidUser() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String jsonUser = mapper.writeValueAsString(userDTO);

        when(userServiceMock.registerNewUser(userDTO)).thenReturn(new User());

        mockMvc.perform(post("/register")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(jsonUser)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
//                .andExpect(jsonPath("$.email").exists());

        verify(userServiceMock).registerNewUser(any(UserDTO.class));
    }

    @Test
    void shouldReturnLoggedUserAsString() throws Exception{
        when(loginDetailsServiceMock.getLoggedUser()).thenReturn(user);

        MvcResult result = mockMvc.perform(get("/main"))
                .andExpect(status().isOk()).andReturn();

        assertEquals("Welcome foo bar!", result.getResponse().getContentAsString());

    }

    @Test
    void shouldReturnUnprocessableEntityWhenUserExists() throws Exception{
        doThrow(EmailExistsException.class).when(userServiceMock).registerNewUser(any(UserDTO.class));

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(new UserErrorResponse());

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonResponse)).andExpect(status().isUnprocessableEntity());
//                .andExpect(jsonPath("$.message").exists());

        verify(userServiceMock).registerNewUser(any(UserDTO.class));

    }
}