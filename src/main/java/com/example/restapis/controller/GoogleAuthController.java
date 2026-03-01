package com.example.restapis.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapis.entity.User;
import com.example.restapis.response.LoginResponse;
import com.example.restapis.security.JwtService;
import com.example.restapis.service.GoogleLoginService;
import com.example.restapis.service.UserDetailsImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class GoogleAuthController {
    @Autowired
    GoogleLoginService googleLoginService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/public/auth/google/login")
    public String loginWithGoogleAuth(HttpServletResponse response) throws java.io.IOException{
        String url = googleLoginService.loginWithGoogle();
    //    response.sendRedirect(url);
       return url;
    }
    @GetMapping("/grantcode")
    public ResponseEntity<LoginResponse> handleCallback(@RequestParam String code){
        Map<String, Object> response =  googleLoginService.handleGoogleCallback(code);
        User user = (User)response.get("user-info");
        System.out.println(user);
         String token = jwtService.generateToken(new UserDetailsImpl(user));
        long expiresIn = jwtService.getExpirationTime(); 

        LoginResponse loginresponse = new LoginResponse();
        loginresponse.setToken(token);
        loginresponse.setId(user.getId());
        loginresponse.setName(user.getName());
        loginresponse.setEmail(user.getEmail());
        loginresponse.setExpiresIn(expiresIn);

        return ResponseEntity.ok(loginresponse);
        
    }
}
