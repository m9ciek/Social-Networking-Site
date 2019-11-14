package com.maciek.socialnetworkingsite.security;

import com.maciek.socialnetworkingsite.dao.UserRepository;
import com.maciek.socialnetworkingsite.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public LoginDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s).orElseThrow(()-> new UsernameNotFoundException(s));
        return LoginDetails.builder().username(user.getEmail()).password(user.getPassword()).build();
    }
}
