package com.maciek.socialnetworkingsite.service.comment;

import com.maciek.socialnetworkingsite.entity.Comment;
import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.exception.PostNotFoundException;
import com.maciek.socialnetworkingsite.repository.CommentRepository;
import com.maciek.socialnetworkingsite.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment addComment(Comment comment, long id) {
        Post post = checkIfPostExistsAndReturn(id);
        comment.setCreated(LocalDateTime.now());
        comment.setPostId(post.getId());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllCommentsForPost(long postId) {
        Post post = checkIfPostExistsAndReturn(postId);
        Optional<List<Comment>> commentsFromDb = commentRepository.findAllByPostId(post.getId());
        if (commentsFromDb.isEmpty()) {
            return List.of(); //returning empty list
        }
        return commentsFromDb.get();
    }

    @Override
    public Comment getCommentById(long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Comment with id:" + id + " has not been found in database")
        );
    }

    private Post checkIfPostExistsAndReturn(long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post with id: " + postId + " has not been found.")
        );
    }
}
