package com.maciek.socialnetworkingsite.service;

import com.maciek.socialnetworkingsite.dao.PostRepository;
import com.maciek.socialnetworkingsite.dao.UserRepository;
import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.exception.EmailNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DefaultPostService implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public DefaultPostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Post addNewPost(String userEmail, String body) {
        Post post = new Post();
        post.setUser(userRepository.findByEmail(userEmail).orElseThrow(EmailNotFoundException::new));
        post.setBody(body);
        postRepository.save(post);
        return post;
    }

    @Override
    @Transactional
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
