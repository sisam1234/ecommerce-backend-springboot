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

import com.example.restapis.dto.LoginUserDTO;
import com.example.restapis.dto.RegisterUserDTO;
import com.example.restapis.dto.UserProfileDTO;
import com.example.restapis.entity.Profile;
import com.example.restapis.entity.User;
import com.example.restapis.repository.ProfileRepository;
import com.example.restapis.repository.UserRepository;
import com.example.restapis.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
public class UserController {

   
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
    
	
	
	@PostMapping("/user/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDTO request ,  BindingResult result) {
	    
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
	public ResponseEntity<String> login(@RequestBody LoginUserDTO request, HttpSession session){
		try {
			User user = userService.login(request);
			session.setAttribute("userId",user.getId());
			
			return ResponseEntity.ok("login successful");
		}catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("user/{id}/profile")
	public ResponseEntity<?> profile(@PathVariable Long id, @RequestBody UserProfileDTO request){
		request.setId(id);
		userService.createProfile(request);
		return ResponseEntity.ok("profile created");
		
	}
	
}
