package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.exception.EmailNotFoundException;
import com.maciek.socialnetworkingsite.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> showAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping("/main/post")
    public ResponseEntity addNewPost(@RequestHeader("email") String userEmail, @RequestBody @Valid String postBody){
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName(); //getting current logged user
        try{
            Post postToAdd = postService.addNewPost(userEmail, postBody);
            return ResponseEntity.ok(postToAdd);
        } catch (EmailNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
