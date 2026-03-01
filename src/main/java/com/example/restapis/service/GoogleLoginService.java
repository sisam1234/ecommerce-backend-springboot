package com.example.restapis.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.restapis.entity.Role;
import com.example.restapis.entity.User;
import com.example.restapis.exception.ResourceNotFoundException;
import com.example.restapis.repository.UserRepository;

@Service
public class GoogleLoginService {

    private WebClient webClient = WebClient.builder().build();
    @Autowired
    private UserRepository userRepository;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String client_secret;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String client_id;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String return_url;
    @Value("${spring.security.oauth2.client.registration.google.scope}")
    private String scope;

    public String loginWithGoogle() {
        String scopes = "openid profile email";
        String authUrl = "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + client_id
                + "&redirect_uri=" + URLEncoder.encode(return_url, StandardCharsets.UTF_8)
                + "&response_type=code"
                + "&scope=" + URLEncoder.encode(scopes, StandardCharsets.UTF_8);

        return authUrl;
    }

    public Map<String, Object> handleGoogleCallback(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("client_id", client_id);
        formData.add("client_secret", client_secret);
        formData.add("redirect_uri", return_url);
        formData.add("grant_type", "authorization_code");

        Map<String, Object> tokenResponse = webClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (tokenResponse == null || !tokenResponse.containsKey("access_token")) {
            throw new RuntimeException("Failed to get access token from Google");
        }

        String accessToken = (String) tokenResponse.get("access_token");

        Map<String, Object> userInfo = webClient.get()
                .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        User user = userRepository.findByEmail((String) userInfo.get("email")).orElseGet(() -> {
            User newuser = new User();
            newuser.setEmail((String) userInfo.get("email"));
            newuser.setName((String) userInfo.get("name"));
            newuser.setPassword(null);
            newuser.setRole(Role.USER);
            return userRepository.save(newuser);

        });
       return  Map.of("message", "Login successful",
        "user-info", user
            
        );
    // return userInfo;
    }

}
