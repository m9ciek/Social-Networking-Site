package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.rest.dto.CommentDTO;
import com.maciek.socialnetworkingsite.rest.dto.mapper.CommentDTOMapper;
import com.maciek.socialnetworkingsite.rest.wrapper.CommentCreationRequest;
import com.maciek.socialnetworkingsite.security.LoginDetailsService;
import com.maciek.socialnetworkingsite.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/comments")
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentCreationRequest commentRequest){
        long userId = loginDetailsService.getLoggedUser().getId();
        long postId = commentRequest.getPostId();
        String content = commentRequest.getContent();
        return ResponseEntity.ok(CommentDTOMapper.mapCommentToDTO(commentService.addComment(postId, userId,content)));
    }
}
