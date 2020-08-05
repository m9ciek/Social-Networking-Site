package com.maciek.socialnetworkingsite.unit.service;

import com.maciek.socialnetworkingsite.entity.Comment;
import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.repository.CommentRepository;
import com.maciek.socialnetworkingsite.repository.PostRepository;
import com.maciek.socialnetworkingsite.service.comment.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepositoryMock;
    @Mock
    private PostRepository postRepositoryMock;

    @InjectMocks
    private CommentServiceImpl commentService;

    private List<Comment> commentsList;
    private Comment comment;

    @BeforeEach
    void setUp() {
        this.comment = new Comment(1L, 1L, 1L, "content one", LocalDateTime.now());
        Comment newComment = new Comment(2L, 1L, 1L, "content two", LocalDateTime.now().plusDays(1));
        commentsList = Arrays.asList(this.comment, newComment);
    }

    @Test
    void shouldAddToCorrectPost_addComment() {
        Post postToAddComment = Post.builder().id(5L).body("Post body")
                .created(LocalDateTime.now().minusDays(1)).imageURL("url/image")
                .comments(null).build();
        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);

        when(postRepositoryMock.findById(anyLong())).thenReturn(Optional.of(postToAddComment));

        commentService.addComment(this.comment, 5L);
        verify(commentRepositoryMock).save(captor.capture());
        Comment capturedComment = captor.getValue();

        assertEquals(5L, capturedComment.getPostId());
    }

    @Test
    void getAllCommentsForPost() {
    }

    @Test
    void getCommentById() {
    }
}