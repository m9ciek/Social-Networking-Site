package com.maciek.socialnetworkingsite.service.post;

import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.PostNotFoundException;
import com.maciek.socialnetworkingsite.repository.CommentRepository;
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
public class DefaultPostService implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private StorageService storageService;
    private CommentRepository commentRepository;
    private static final int PAGE_SIZE = 20;

    @Autowired
    public DefaultPostService(PostRepository postRepository, UserRepository userRepository, StorageService storageService, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.storageService = storageService;
        this.commentRepository = commentRepository;
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
        if(userFromDb.isEmpty()){
            throw new UsernameNotFoundException("User with id: " + userId + " has not been found in database");
        }
        return userFromDb.get().getPosts();
    }


    @Override
    @Transactional
    public Post addNewPost(long userId, String body, MultipartFile image){
        Post post = new Post();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User with id: " + userId + " has not been found in database.")
        );

        post.setUserId(userId);
        post.setBody(body);
        post.setCreated(LocalDateTime.now());
        post.setImageURL(storageService.store(image));
        postRepository.save(post);

        return post;
    }

//    @Override
//    public Comment addNewComment(String content, long postId, long userId) throws RuntimeException {
//        Post post = getPostById(postId);
//
//        Comment newComment = new Comment();
//        newComment.setPostId(post.getId());
//        newComment.setContent(content);
//        newComment.setCreated(LocalDateTime.now());
//        newComment.setUserId(userId);
//        commentRepository.save(newComment);
//
//        return newComment;
//    }
//
//    @Override
//    public List<Comment> getCommentsForPostId(long id) {
//        Post post = getPostById(id);
//        return post.getComments();
//    }

}
