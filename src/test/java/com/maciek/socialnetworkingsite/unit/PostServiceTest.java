package com.maciek.socialnetworkingsite.unit;

import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.exception.PostNotFoundException;
import com.maciek.socialnetworkingsite.repository.PostRepository;
import com.maciek.socialnetworkingsite.repository.UserRepository;
import com.maciek.socialnetworkingsite.service.post.PostServiceImpl;
import com.maciek.socialnetworkingsite.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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
    void setUp(){
        this.post = new Post(1L,1L, "body", LocalDateTime.now().minusDays(1), "url/image", null);
        Post post2 = new Post(2L,1L, "body", LocalDateTime.now().plusDays(1), null, null);
        Post post3 = new Post(3L,1L, "body", LocalDateTime.now().plusDays(2), null, null);
        postsList = Arrays.asList(post, post2, post3);
    }

    @Test
    void shouldGetAllPostsSortedFromNewestToOldest() {
        when(postRepositoryMock.findAllPosts(PageRequest.of(0, 20))).thenReturn(this.postsList); //page size greater than 1

        List<Post> sortedList = postsList.stream().sorted(Comparator.comparing(Post::getCreated).reversed()).collect(Collectors.toList());

        assertAll(
                () -> assertEquals(sortedList.get(0), postService.getAllPosts(0).get(0)), //newest post on first position, oldest last
                () -> assertEquals(sortedList.get(2), postService.getAllPosts(0).get(2))
        );
    }

    @Test
    void shouldGetPostById() {
        when(postRepositoryMock.findById(anyLong())).thenReturn(Optional.of(this.post));
        assertEquals(this.post, postService.getPostById(123));
    }

    @Test
    void shouldThrowPostNotFoundException(){
        when(postRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class, () -> postService.getPostById(1500_100_900));
    }

    @Test
    void getPostsForUser() {
    }

    @Test
    void addNewPost() {
    }

    @Test
    void updatePost() {
    }

    @Test
    void deletePost() {
    }
}