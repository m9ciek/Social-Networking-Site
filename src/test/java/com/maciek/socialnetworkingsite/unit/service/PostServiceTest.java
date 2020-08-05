package com.maciek.socialnetworkingsite.unit.service;

import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.PostNotFoundException;
import com.maciek.socialnetworkingsite.repository.PostRepository;
import com.maciek.socialnetworkingsite.repository.UserRepository;
import com.maciek.socialnetworkingsite.service.post.PostServiceImpl;
import com.maciek.socialnetworkingsite.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private StorageService storageServiceMock;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post;
    private List<Post> postsList;

    @BeforeEach
    void setUp() {
        this.post = new Post(1L, 1L, "body", LocalDateTime.now().minusDays(1), "url/image", null);
        Post post2 = new Post(2L, 1L, "body", LocalDateTime.now().plusDays(1), null, null);
        Post post3 = new Post(3L, 1L, "body", LocalDateTime.now().plusDays(2), null, null);
        postsList = Arrays.asList(post, post2, post3);
    }

    @Test
    void shouldGetAllSortedFromNewestToOldest_getAllPosts() {
        when(postRepositoryMock.findAllPosts(PageRequest.of(0, 20))).thenReturn(this.postsList); //page size greater than 1

        List<Post> sortedList = postsList.stream().sorted(Comparator.comparing(Post::getCreated).reversed()).collect(Collectors.toList());

        assertAll(
                () -> assertEquals(sortedList.get(0), postService.getAllPosts(0).get(0)), //newest post on first position, oldest last
                () -> assertEquals(sortedList.get(2), postService.getAllPosts(0).get(2))
        );
    }

    @Test
    void shouldGet_getPostById() {
        when(postRepositoryMock.findById(anyLong())).thenReturn(Optional.of(this.post));
        assertEquals(this.post, postService.getPostById(123L));
    }

    @Test
    void shouldThrowPostNotFoundException_getPostById() {
        when(postRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class, () -> postService.getPostById(1500_100_900L));
    }

    @Test
    void shouldReturn_getPostsForUser() {
        User userWithPosts = new User(1L, "foo", "bar", "foo@bar.com", "123", this.postsList, null);
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(userWithPosts));
        assertEquals(this.postsList, postService.getPostsForUser(1234L));
    }

    @Test
    void shouldThrowUsernameNotFoundException_getPostsForUser() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> postService.getPostsForUser(1234L));
    }

    @Test
    void shouldCorrectlyAddAndReturn_addNewPost() {
        byte[] fileByteCode = {1, 2, 1};
        MultipartFile multipartFile = new MockMultipartFile("file", fileByteCode);

        when(storageServiceMock.store(any())).thenReturn("/url/post");
        when(postRepositoryMock.save(any(Post.class))).thenReturn(this.post);

        assertEquals(this.post, postService.addNewPost(this.post, multipartFile));
        verify(postRepositoryMock).save(any(Post.class));
    }

    @Test
    void shouldUpdateBodyAndReturn_updatePost() {
        Post updatedPost = new Post(8L, 5L, "Updated Post Body", LocalDateTime.now(), null, null);
        final ArgumentCaptor<Post> captor = ArgumentCaptor.forClass(Post.class);

        when(postRepositoryMock.findById(anyLong())).thenReturn(Optional.of(this.post));
        when(postRepositoryMock.save(any(Post.class))).thenReturn(updatedPost);

        postService.updatePost(updatedPost);
        verify(postRepositoryMock).save(captor.capture());
        Post capturedPost = captor.getValue();

        assertEquals(capturedPost.getBody(), updatedPost.getBody());
    }

    @Test
    void deletePost() {
        doNothing().when(postRepositoryMock).deleteById(anyLong());
        postService.deletePost(1500_100_900L);
        verify(postRepositoryMock).deleteById(anyLong());
    }
}