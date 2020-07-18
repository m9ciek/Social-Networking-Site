package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.entity.Comment;
import com.maciek.socialnetworkingsite.rest.dto.CommentDTO;
import com.maciek.socialnetworkingsite.rest.dto.mapper.CommentDTOMapper;
import com.maciek.socialnetworkingsite.security.LoginDetailsService;
import com.maciek.socialnetworkingsite.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final LoginDetailsService loginDetailsService;

    @Autowired
    public CommentController(CommentService commentService, LoginDetailsService loginDetailsService) {
        this.commentService = commentService;
        this.loginDetailsService = loginDetailsService;
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long id){
        return ResponseEntity.ok(CommentDTOMapper.mapCommentToDTO(commentService.getCommentById(id)));
    }

    @GetMapping("/comments/posts/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsForPostId(@PathVariable long id){
        return ResponseEntity.ok(CommentDTOMapper.mapCommentsToDTOs(commentService.getAllCommentsForPost(id)));
    }

    @PostMapping("/comments/posts/{id}")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment, @PathVariable long id){
        try {
            comment.setUserId(loginDetailsService.getLoggedUser().getId());
            return ResponseEntity.ok(commentService.addComment(comment, id));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
