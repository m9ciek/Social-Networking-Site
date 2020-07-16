package com.maciek.socialnetworkingsite.service.comment;

import com.maciek.socialnetworkingsite.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(long postId, long userId, String content);
    List<Comment> getAllCommentsForPost(long postId);
    Comment getCommentById(long id);
}
