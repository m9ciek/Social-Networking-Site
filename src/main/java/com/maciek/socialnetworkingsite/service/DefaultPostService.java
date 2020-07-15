package com.maciek.socialnetworkingsite.service;

import com.maciek.socialnetworkingsite.entity.Comment;
import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.EmailNotFoundException;
import com.maciek.socialnetworkingsite.repository.CommentRepository;
import com.maciek.socialnetworkingsite.repository.PostRepository;
import com.maciek.socialnetworkingsite.repository.UserRepository;
import com.maciek.socialnetworkingsite.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DefaultPostService implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private StorageService storageService;
    private CommentRepository commentRepository;

    @Autowired
    public DefaultPostService(PostRepository postRepository, UserRepository userRepository, StorageService storageService, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.storageService = storageService;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public Post addNewPost(String userEmail, String body, MultipartFile image) throws EmailNotFoundException{
        Post post = new Post();
        User user = userRepository.findByEmail(userEmail).orElseThrow(EmailNotFoundException::new);

//        post.setUser(user);
        post.setBody(body);
        post.setCreated(LocalDateTime.now());
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
        List<Post> listOfAllPosts = postRepository.findAll();
        listOfAllPosts.sort(Collections.reverseOrder(Comparator.comparing(Post::getCreated))); //default sorting from newest to oldest
        return listOfAllPosts;
    }

    @Override
    @Transactional
    public List<Post> getPostsForUser(long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        if(userFromDb.isEmpty()){
            throw new UsernameNotFoundException("User with id: " + userId + " has not been found in database");
        }
        return userFromDb.get().getPosts();
    }

    @Override
    @Transactional
    public Comment addNewComment(String content, long postId, long userId) throws RuntimeException {
        Post post = getSinglePostById(postId);

        Comment newComment = new Comment();
        newComment.setPostId(post.getId());
        newComment.setContent(content);
        newComment.setCreated(LocalDateTime.now());
        newComment.setUserId(userId);
        commentRepository.save(newComment);

        return newComment;
    }

    @Override
    @Transactional
    public List<Comment> getCommentsForPostId(long id) {
        Post post = getSinglePostById(id);
        return post.getComments();
    }

    private Post getSinglePostById(long postId) {
        Optional<Post> postFromDb = postRepository.findById(postId);
        if (postFromDb.isEmpty()) {
            throw new RuntimeException("Post with id: " + postId + " has not been found in database");
        }
        return postFromDb.get();
    }

}
