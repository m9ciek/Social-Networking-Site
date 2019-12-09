package com.maciek.socialnetworkingsite.service;

import com.maciek.socialnetworkingsite.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    Post addNewPost(String username, String body, MultipartFile image);
    List<Post> getAllPosts();
}
