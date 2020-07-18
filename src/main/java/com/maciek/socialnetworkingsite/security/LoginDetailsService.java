package com.maciek.socialnetworkingsite.security;

import com.maciek.socialnetworkingsite.entity.User;
import com.maciek.socialnetworkingsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public LoginDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(s));
        return LoginDetails.builder().username(user.getEmail()).password(user.getPassword()).build();
    }

    public User getLoggedUser() throws SecurityException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> loggedUser = userRepository.findByEmail(authentication.getName());
        if (loggedUser.isPresent()) {
            return loggedUser.get();
        }
        throw new SecurityException("User was not found in database");
    }

}
