package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.exception.EmailNotFoundException;
import com.maciek.socialnetworkingsite.security.LoginDetailsService;
import com.maciek.socialnetworkingsite.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity addNewPost(@RequestBody @Valid String postBody){
        String userEmail = loginDetailsService.getLoggedUser().getEmail();
        try{
            Post postToAdd = postService.addNewPost(userEmail, postBody);
            return ResponseEntity.ok(postToAdd);
        } catch (EmailNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
