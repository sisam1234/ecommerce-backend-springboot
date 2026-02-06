package com.example.restapis.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapis.dto.LoginResponse;
import com.example.restapis.dto.LoginUserDTO;
import com.example.restapis.dto.RegisterUserDTO;
import com.example.restapis.entity.User;
import com.example.restapis.repository.UserRepository;
import com.example.restapis.security.JwtService;
import com.example.restapis.service.UserDetailsImpl;
import com.example.restapis.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDTO request, BindingResult result) {

        if (result.hasErrors()) {
            List<String> error = result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(error);
        }
        if (userRepo.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("email is already registered");
        }

        userService.register(request);
        return ResponseEntity.ok("User registered successfully");

    }

    @PostMapping("/user/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDTO req) {
        User user = userService.login(req);
        String token = jwtService.generateToken(new UserDetailsImpl(user));
        long expiresIn = jwtService.getExpirationTime(); // your JwtService should expose this

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setExpiresIn(expiresIn);

        return ResponseEntity.ok(response);

    }
}
