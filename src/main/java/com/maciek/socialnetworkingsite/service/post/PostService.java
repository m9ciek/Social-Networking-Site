package com.maciek.socialnetworkingsite.service.post;

import com.maciek.socialnetworkingsite.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts(int page);
    Post getPostById(long postId);
    Post addNewPost(long userId, String body, MultipartFile image);
    List<Post> getPostsForUser(long userId);
}
