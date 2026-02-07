package com.example.restapis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.restapis.repository.UserRepository;
import com.example.restapis.service.UserDetailsImpl;

@Component
public class AuthUtil {
    @Autowired
    UserRepository userRepository;
    public Long loggedInUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
       return user.getId();
    }
}
