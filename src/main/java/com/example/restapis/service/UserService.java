package com.example.restapis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.restapis.dto.LoginUserDTO;
import com.example.restapis.dto.RegisterUserDTO;
import com.example.restapis.dto.UserProfileDTO;
import com.example.restapis.entity.Profile;
import com.example.restapis.entity.Role;
import com.example.restapis.entity.User;
import com.example.restapis.exception.EmailAlreadyExistsException;
import com.example.restapis.exception.InvalidCredentialsException;
import com.example.restapis.exception.UserNotFoundException;
import com.example.restapis.repository.ProfileRepository;
import com.example.restapis.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private ProfileRepository profileRepo;
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	public User register(RegisterUserDTO req) {
		if (userRepo.existsByEmail(req.getEmail())) {
			throw new EmailAlreadyExistsException("Email alread exist");
		}
		User user = new User();
		user.setName(req.getName());
		user.setEmail(req.getEmail());
		user.setPassword(passwordEncoder.encode(req.getPassword()));
		user.setRole(Role.USER);
		return userRepo.save(user);

	}

	public User login(LoginUserDTO request) {

		User user = userRepo.findByEmail(request.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User not found"));
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getEmail(),
							request.getPassword()));
		} catch (BadCredentialsException ex) {
			throw new InvalidCredentialsException(ex.getMessage());
		}
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
