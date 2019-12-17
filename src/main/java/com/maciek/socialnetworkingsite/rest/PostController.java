package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.exception.EmailNotFoundException;
import com.maciek.socialnetworkingsite.security.LoginDetailsService;
import com.maciek.socialnetworkingsite.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController {

    private PostService postService;
    private LoginDetailsService loginDetailsService;

    @Autowired
    public PostController(PostService postService, LoginDetailsService loginDetailsService) {
        this.postService = postService;
        this.loginDetailsService = loginDetailsService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> showAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping("/main/post")
    public ResponseEntity addNewPost(@RequestParam(value = "body") @Valid String postBody,
                                     @RequestParam(value = "image", required = false) MultipartFile image){

        String userEmail = loginDetailsService.getLoggedUser().getEmail();
        Post postToAdd = postService.addNewPost(userEmail, postBody, image);
        return ResponseEntity.ok(postToAdd);
    }

}
