package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.rest.dto.CommentDTO;
import com.maciek.socialnetworkingsite.rest.dto.mapper.CommentDTOMapper;
import com.maciek.socialnetworkingsite.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long id){
        return ResponseEntity.ok(CommentDTOMapper.mapCommentToDTO(commentService.getCommentById(id)));
    }

    @GetMapping("/comments/posts/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsForPostId(@PathVariable long id){
        return ResponseEntity.ok(CommentDTOMapper.mapCommentsToDTOs(commentService.getAllCommentsForPost(id)));
    }
}
