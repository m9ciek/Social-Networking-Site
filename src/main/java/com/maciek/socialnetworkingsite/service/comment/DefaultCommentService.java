package com.maciek.socialnetworkingsite.service.comment;

import com.maciek.socialnetworkingsite.entity.Comment;
import com.maciek.socialnetworkingsite.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultCommentService implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public DefaultCommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment addComment(long postId, long userId, String content) {
        Comment comment = new Comment(0,postId,userId,content, LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllCommentsForPost(long postId) {
        Optional<List<Comment>> commentsFromDb = commentRepository.findAllByPostId(postId);
        if(commentsFromDb.isEmpty()){
            return List.of(); //returning empty list
        }
        return commentsFromDb.get();
    }

    @Override
    public Comment getCommentById(long id) {
        return commentRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Comment with id:" + id + " has not been found in database")
        );
    }
}
