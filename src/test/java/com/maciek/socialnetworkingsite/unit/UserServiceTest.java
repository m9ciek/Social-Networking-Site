package com.maciek.socialnetworkingsite.unit;

import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.UserExistsException;
import com.maciek.socialnetworkingsite.repository.PostRepository;
import com.maciek.socialnetworkingsite.repository.UserRepository;
import com.maciek.socialnetworkingsite.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private PostRepository postRepositoryMock;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User(1L, "Foo", "Bar", "foo@bar.com", "qwe123", null, null);
    }

    @Test
    void shouldRegisterAndReturnUser() {
        when(userRepositoryMock.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(userRepositoryMock.save(this.user)).thenReturn(this.user);
        assertEquals(user, userService.registerUser(this.user));

        verify(userRepositoryMock).findByEmail("foo@bar.com");
    }

    @Test
    void shouldThrowUserExistsExceptionWhenRegistering() {
        when(userRepositoryMock.findByEmail(any(String.class))).thenReturn(Optional.of(this.user));
        assertThrows(UserExistsException.class, () -> userService.registerUser(this.user));
    }

    @Test
    void shouldReturnAllUsers() {
        User user2 = new User(2L, "Foo", "Bar", "foo@bar.com", "password", null, null);
        User user3 = new User(3L, "Bar", "Foo", "bar@foo.com", "password", null, null);
        List<User> userList = Arrays.asList(this.user, user2, user3);

        when(userRepositoryMock.findAllUsers(PageRequest.of(0, 10))).thenReturn(userList);
        assertAll(
                () -> assertEquals(userList, userService.getUsers(0)), //0 default page
                () -> assertEquals(userList.size(), userService.getUsers(0).size())
        );

    }

    @Test
    void shouldReturnUsersWithPosts() {
        Post post = new Post(1L, 2L, "foobar", LocalDateTime.now(), null, null);
        List<Post> userPosts = Arrays.asList(post, post); //same 2 posts in list

        User user2 = new User(2L, "Foo", "Bar", "foo@bar.com", "password", userPosts, null);
        User user3 = new User(3L, "Bar", "Foo", "bar@foo.com", "password", userPosts, null);
        List<User> userList = Arrays.asList(user2, user3);

        when(userRepositoryMock.findAllUsers(PageRequest.of(0, 10))).thenReturn(userList);
        when(postRepositoryMock.findAllByUserIdIn(anyList())).thenReturn(userPosts);

        List<Post> extractedPostsFromUser = userService.getUsersWithPosts(0).stream().map(User::getPosts).findFirst().get();

        assertAll(
                () -> assertEquals(2, userService.getUsersWithPosts(0).size()),
                () -> assertEquals(userPosts, extractedPostsFromUser)
        );
    }

    @Test
    void shouldReturnUserById() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(this.user));
        assertEquals(this.user, userService.getUserById(150));
    }

    @Test
    void shouldThrowUserUsernameNotFoundException() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.getUserById(150));
    }

    @Test
    void shouldInvokeDeleteByIdMethod() {
        doNothing().when(userRepositoryMock).deleteById(anyLong());
        userService.deleteUser(150);
        verify(userRepositoryMock).deleteById(anyLong());
    }
}