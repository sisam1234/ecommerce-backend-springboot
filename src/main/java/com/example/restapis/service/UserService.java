package com.example.restapis.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restapis.dto.request.LoginUserRequest;
import com.example.restapis.dto.request.RegisterUserRequest;
import com.example.restapis.dto.request.UserProfileRequest;
import com.example.restapis.entity.Profile;
import com.example.restapis.entity.User;
import com.example.restapis.repository.ProfileRepository;
import com.example.restapis.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProfileRepository profileRepo;
	
	public User register(RegisterUserRequest req) {
		User user = new User();
		user.setName(req.getName());
		user.setEmail(req.getEmail());
		user.setPassword(req.getPassword());
		return userRepo.save(user);
        
	}
	
	public User login(LoginUserRequest request) {
		
		User user = userRepo.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("Inavlid credentials.."));
		if(!user.getPassword().equals(request.getPassword())) {
			throw new RuntimeException("Incorrect Password");
		}
	return user;
		
	}
	
	public Profile createProfile(UserProfileRequest request) {
		User u = userRepo.findById(request.getId()).orElseThrow();
		Profile p = new Profile();
		p.setDateOfBirth(request.getDateOfBirth());
		p.setPhone(request.getPhone());
		p.setUser(u);
		return profileRepo.save(p);
		
	}
	
}
