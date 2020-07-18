package com.maciek.socialnetworkingsite.service.comment;

import com.maciek.socialnetworkingsite.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllCommentsForPost(long postId);

    Comment getCommentById(long id);

    Comment addComment(Comment comment, long id);
}
