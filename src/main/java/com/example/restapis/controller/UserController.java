package com.example.restapis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapis.dto.request.RegisterUserRequest;
import com.example.restapis.entity.User;
import com.example.restapis.repository.UserRepository;
import com.example.restapis.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
	@PostMapping("/user/register")
	public ResponseEntity<String> register(@RequestBody RegisterUserRequest request) {
	    userService.register(request);
	    return ResponseEntity.ok("User registered successfully");

	}
}
