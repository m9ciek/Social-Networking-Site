package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.rest.dto.PostDTO;
import com.maciek.socialnetworkingsite.rest.dto.mapper.PostDTOMapper;
import com.maciek.socialnetworkingsite.rest.wrapper.PostCreationRequest;
import com.maciek.socialnetworkingsite.security.LoginDetailsService;
import com.maciek.socialnetworkingsite.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;
    private final LoginDetailsService loginDetailsService;

    @Autowired
    public PostController(PostService postService, LoginDetailsService loginDetailsService) {
        this.postService = postService;
        this.loginDetailsService = loginDetailsService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false) Integer page){
        int pageNumber = page != null && page > 0 ? page : 0;
        List<Post> posts = postService.getAllPosts(pageNumber);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable long id){
        return ResponseEntity.ok(PostDTOMapper.mapPostToDTO(postService.getPostById(id)));
    }

    @PostMapping("/posts")
    public ResponseEntity addNewPost(@RequestBody PostCreationRequest postRequest,
                                     @RequestParam(value = "image", required = false) MultipartFile image){

        return ResponseEntity.ok(postService.addNewPost(postRequest.getUserId(), postRequest.getPostBody(), image));
    }

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsForUser(@PathVariable long userId){
        return ResponseEntity.ok(PostDTOMapper.mapPostsToDTOs(postService.getPostsForUser(userId)));
    }

//    @GetMapping("/posts/{postId}/comments")
//    public ResponseEntity<?> getCommentsForPostId(@PathVariable long postId){
//        List<Comment> comments;
//        try{
//             comments = postService.getCommentsForPostId(postId);
//        }catch (RuntimeException e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(comments);
//    }
//
//    @PostMapping("/posts/comments")
//    public ResponseEntity<Comment> addNewComment(@RequestParam long postId, @RequestBody String content){
//        long userId = loginDetailsService.getLoggedUser().getId();
//        Comment newComment;
//        try{
//            newComment = postService.addNewComment(content,postId, userId);
//        }catch (RuntimeException e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
//    }

}
