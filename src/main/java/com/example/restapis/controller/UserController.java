package com.example.restapis.controller;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.restapis.dto.request.LoginUserRequest;
import com.example.restapis.dto.request.RegisterUserRequest;
import com.example.restapis.dto.request.UserProfileRequest;
import com.example.restapis.entity.Profile;
import com.example.restapis.repository.ProfileRepository;
import com.example.restapis.repository.UserRepository;
import com.example.restapis.service.UserService;


import jakarta.validation.Valid;

@RestController
public class UserController {

   
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
    
	@Autowired
	private ProfileRepository pr;
	
	@PostMapping("/user/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterUserRequest request ,  BindingResult result) {
	    
		 if(result.hasErrors()) {
		    	List<String> error = result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
		    	return ResponseEntity.badRequest().body(error);
		    }
		if(userRepo.existsByEmail(request.getEmail())) {
	    	return ResponseEntity.badRequest().body("email is already registered");
	    }
	   
		userService.register(request);
	    return ResponseEntity.ok("User registered successfully");

	}
	
	@PostMapping("/user/login")
	public ResponseEntity<String> login(@RequestBody LoginUserRequest request){
		try {
			userService.login(request);
			return ResponseEntity.ok("login successful");
		}catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("user/{id}/profile")
	public ResponseEntity<?> profile(@PathVariable Long id, @RequestBody UserProfileRequest request){
		request.setId(id);
		userService.createProfile(request);
		return ResponseEntity.ok("profile created");
		
	}
	@GetMapping("/profile/{id}")
	public Profile profileget(@PathVariable Long id) {
		Profile f = pr.findById(id).orElseThrow();
		System.out.println(f.getUser().getName());
		return f;
		
	}
}
