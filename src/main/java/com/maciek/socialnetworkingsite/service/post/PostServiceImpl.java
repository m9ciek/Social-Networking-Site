package com.maciek.socialnetworkingsite.service.post;

import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.PostNotFoundException;
import com.maciek.socialnetworkingsite.repository.PostRepository;
import com.maciek.socialnetworkingsite.repository.UserRepository;
import com.maciek.socialnetworkingsite.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private StorageService storageService;
    private static final int PAGE_SIZE = 20;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, StorageService storageService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.storageService = storageService;
    }

    @Override
    public List<Post> getAllPosts(int page) {
        List<Post> listOfAllPosts = postRepository.findAllPosts(PageRequest.of(page, PAGE_SIZE));
        listOfAllPosts.sort(Collections.reverseOrder(Comparator.comparing(Post::getCreated))); //default sorting from newest to oldest
        return listOfAllPosts;
    }

    @Override
    public Post getPostById(long postId) {
        Optional<Post> postFromDb = postRepository.findById(postId);
        if (postFromDb.isEmpty()) {
            throw new PostNotFoundException("Post with id: " + postId + " has not been found in database");
        }
        return postFromDb.get();
    }

    @Override
    public List<Post> getPostsForUser(long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        if (userFromDb.isEmpty()) {
            throw new UsernameNotFoundException("User with id: " + userId + " has not been found in database");
        }
        return userFromDb.get().getPosts();
    }

    @Override
    @Transactional
    public Post addNewPost(Post post, MultipartFile image) {
        post.setCreated(LocalDateTime.now());
        post.setImageURL(storageService.store(image));
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post updatePost(Post post) {
        Post postEdited = postRepository.findById(post.getId()).orElseThrow();
        postEdited.setBody(post.getBody());
        return postRepository.save(postEdited);
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }
}
