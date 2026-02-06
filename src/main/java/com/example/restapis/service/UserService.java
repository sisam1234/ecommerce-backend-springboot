package com.example.restapis.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.restapis.dto.LoginUserDTO;
import com.example.restapis.dto.RegisterUserDTO;
import com.example.restapis.dto.UserProfileDTO;
import com.example.restapis.entity.Profile;
import com.example.restapis.entity.Role;
import com.example.restapis.entity.User;
import com.example.restapis.repository.ProfileRepository;
import com.example.restapis.repository.UserRepository;

@Service
public class UserService {


	
	@Autowired
	private ProfileRepository profileRepo;
	 private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
	 private final AuthenticationManager authenticationManager;

    
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
    }

	
	public User register(RegisterUserDTO req) {
		User user = new User();
		user.setName(req.getName());
		user.setEmail(req.getEmail());
		user.setPassword(passwordEncoder.encode(req.getPassword()));
		user.setRole(Role.USER);
		return userRepo.save(user);
        
	}
	
	public User login(LoginUserDTO request) {
		System.out.println(request.getEmail());
		
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				request.getEmail(),
				request.getPassword()
			)
		);
		 User user = userRepo.findByEmail(request.getEmail()).orElseThrow();
    System.out.println("Login successful for user: " + user.getEmail());
    return user;
		
	}
	
	
	public Profile createProfile(UserProfileDTO request) {
		User u = userRepo.findById(request.getId()).orElseThrow();
		Profile p = new Profile();
		p.setDateOfBirth(request.getDateOfBirth());
		p.setPhone(request.getPhone());
		p.setUser(u);
		return profileRepo.save(p);
		
	}
	
}
