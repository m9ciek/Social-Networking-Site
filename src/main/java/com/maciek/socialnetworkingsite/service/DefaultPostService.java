package com.maciek.socialnetworkingsite.service;

import com.maciek.socialnetworkingsite.dao.PostRepository;
import com.maciek.socialnetworkingsite.dao.UserRepository;
import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.EmailNotFoundException;
import com.maciek.socialnetworkingsite.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultPostService implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private StorageService storageService;

    @Autowired
    public DefaultPostService(PostRepository postRepository, UserRepository userRepository, StorageService storageService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.storageService = storageService;
    }

    @Override
    @Transactional
    public Post addNewPost(String userEmail, String body, MultipartFile image) throws EmailNotFoundException{
        Post post = new Post();
        User user = userRepository.findByEmail(userEmail).orElseThrow(EmailNotFoundException::new);

        post.setUser(user);
        post.setBody(body);
        post.setDate(LocalDateTime.now());
        post.setImageURL(storageService.store(image));
        postRepository.save(post);

        List<Post> userPosts = user.getPosts();
        userPosts.add(post);
        user.setPosts(userPosts);
        userRepository.save(user);

        return post;
    }

    @Override
    @Transactional
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

}
