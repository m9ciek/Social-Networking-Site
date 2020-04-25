package com.maciek.socialnetworkingsite.rest;

import com.maciek.socialnetworkingsite.security.*;
import com.maciek.socialnetworkingsite.security.jwt.AuthenticationRequest;
import com.maciek.socialnetworkingsite.security.jwt.AuthenticationResponse;
import com.maciek.socialnetworkingsite.security.jwt.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtilService jwtUtilService;

    @Autowired
    LoginDetailsService loginDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new Exception("Invalid username or password", e);
        }
        final UserDetails userDetails = loginDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtilService.createToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
