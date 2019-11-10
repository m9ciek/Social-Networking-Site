package com.maciek.socialnetworkingsite.service;

import com.maciek.socialnetworkingsite.entity.Post;

import java.util.List;

public interface PostService {
    Post addNewPost(String username, String body);
    List<Post> getAllPosts();
}
