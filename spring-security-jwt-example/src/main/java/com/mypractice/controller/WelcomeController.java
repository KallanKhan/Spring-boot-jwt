package com.mypractice.controller;

import com.mypractice.entity.AuthRequest;
import com.mypractice.entity.User;
import com.mypractice.service.CustomUserDetailsService;
import com.mypractice.utils.Jwtutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
@RestController
public class WelcomeController {
    @Autowired
    private Jwtutils jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService service;
    @GetMapping("/")
    public String welcome(@RequestBody User user) {
        return "Welcome to  !!"+ user.getUserName();

    }
    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.registerUser(user);
    }
}
