package com.example.restapis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restapis.dto.request.RegisterUserRequest;
import com.example.restapis.entity.User;
import com.example.restapis.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	public User register(RegisterUserRequest req) {
		User user = new User();
		user.setName(req.getName());
		user.setEmail(req.getEmail());
		user.setPassword(req.getPassword());
		return userRepo.save(user);
        
	}
}
