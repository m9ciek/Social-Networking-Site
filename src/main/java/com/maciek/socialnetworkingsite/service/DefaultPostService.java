package com.maciek.socialnetworkingsite.service;

import com.maciek.socialnetworkingsite.dao.PostRepository;
import com.maciek.socialnetworkingsite.dao.UserRepository;
import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.exception.EmailNotFoundException;
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

    @Autowired
    public DefaultPostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Post addNewPost(String userEmail, String body, MultipartFile image) {
        Post post = new Post();
        post.setUser(userRepository.findByEmail(userEmail).orElseThrow(EmailNotFoundException::new));
        post.setBody(body);
        post.setDate(LocalDateTime.now());
        if(image!=null) {
            try {
                addImage(image, post);
            } catch (IOException e) {
                e.getMessage();
            }
        }
        postRepository.save(post);
        return post;
    }

    @Override
    @Transactional
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    private void addImage(MultipartFile file, Post post) throws IOException {
        if(file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
            String resourcesPath = System.getProperty("user.dir")+ "/uploads";
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(resourcesPath, fileName);
            Files.write(path, file.getBytes());
            post.setImageURL(path.toString().trim());
        }else{
            throw new IOException("Wrong file type or something went wrong");
        }
    }
}
