package com.example.restapis.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.restapis.dto.UserProfileDTO;


import com.example.restapis.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("user/{id}/profile")
	public ResponseEntity<?> profile(@PathVariable Long id, @RequestBody UserProfileDTO request) {
		request.setId(id);
		userService.createProfile(request);
		return ResponseEntity.ok("profile created");

	}

}
