package com.maciek.socialnetworkingsite.service;

import com.maciek.socialnetworkingsite.entity.Post;
import com.maciek.socialnetworkingsite.repository.PostRepository;
import com.maciek.socialnetworkingsite.rest.dto.UserDTO;
import com.maciek.socialnetworkingsite.rest.dto.mapper.UserDTOMapper;
import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.exception.UserExistsException;
import com.maciek.socialnetworkingsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private PasswordEncoder passwordEncoder;

    private static final int PAGE_SIZE = 10;

    @Autowired
    public DefaultUserService(UserRepository userRepository, PostRepository postRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerNewUser(UserDTO accountDTO){
//        Optional<User> databaseUser = userRepository.findByEmail(accountDTO.getEmail());
////        if(databaseUser.isPresent()){
////            throw new UserExistsException("User with email: " + databaseUser.get().getEmail() +" already exists.");
////        }
////        User user = UserDTOMapper.mapDtoToUser(accountDTO, passwordEncoder);
////        return userRepository.save(user);
        return null;
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

    private List<Post> extractPost(List<Post> posts, long userId) {
        return posts.stream()
                .filter(post -> post.getUserId() == userId)
                .collect(Collectors.toList());
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
}
