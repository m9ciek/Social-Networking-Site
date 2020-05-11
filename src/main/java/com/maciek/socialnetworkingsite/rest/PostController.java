package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.entity.Comment;
import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.security.LoginDetailsService;
import com.maciek.socialnetworkingsite.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/posts/{id}")
    public ResponseEntity<List<Post>> showPostsForUser(@PathVariable long id){
        return ResponseEntity.ok(postService.getPostsForUser(id));
    }

    @PostMapping("/posts/comment")
    public ResponseEntity<Comment> addNewComment(@RequestParam long postId, @RequestBody String content){
        long userId = loginDetailsService.getLoggedUser().getId();
        Comment newComment;
        try{
            newComment = postService.addNewComment(content,postId, userId);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

}
