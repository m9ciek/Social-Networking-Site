package com.maciek.socialnetworkingsite.service.user;

import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.UserExistsException;
import com.maciek.socialnetworkingsite.repository.PostRepository;
import com.maciek.socialnetworkingsite.repository.UserRepository;
import com.maciek.socialnetworkingsite.rest.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    private static final int PAGE_SIZE = 10;

    @Autowired
    public DefaultUserService(UserRepository userRepository, PostRepository postRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User registerUser(User user){
        Optional<User> databaseUser = userRepository.findByEmail(user.getEmail());
        if(databaseUser.isPresent()){
            throw new UserExistsException("User with email: " + databaseUser.get().getEmail() +" already exists.");
        }
        user.setId(0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers(int page) {
        return userRepository.findAllUsers(PageRequest.of(page, PAGE_SIZE));
    }

    @Override
    public List<User> getUsersWithPosts(int page) {
        List<User> allUsers = userRepository.findAllUsers(PageRequest.of(page, PAGE_SIZE));
        List<Long> userIds = allUsers.stream()
                .map(User::getId)
                .collect(Collectors.toList());
        List<Post> posts = postRepository.findAllByUserIdIn(userIds);
        allUsers.forEach(user -> user.setPosts(extractPost(posts, user.getId())));
        return allUsers;
    }

    @Override
    public User getUserById(long id){
        Optional<User> userFromDatabase = userRepository.findById(id);
        if(userFromDatabase.isPresent()){
            return userFromDatabase.get();
        }else {
            throw new UsernameNotFoundException("User with id: " + id + " has not been found in database.");
        }
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    private List<Post> extractPost(List<Post> posts, long userId) {
        return posts.stream()
                .filter(post -> post.getUserId() == userId)
                .collect(Collectors.toList());
    }
}
