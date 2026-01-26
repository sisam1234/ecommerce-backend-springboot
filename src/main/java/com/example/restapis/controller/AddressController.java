package com.example.restapis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapis.dto.AddressDTO;
import com.example.restapis.entity.User;
import com.example.restapis.repository.UserRepository;
import com.example.restapis.service.AddressService;

import jakarta.servlet.http.HttpSession;

@RestController
public class AddressController {
	@Autowired
	AddressService addressService;
	
	@Autowired
	UserRepository userRepository;

	@PostMapping("address")
	public ResponseEntity<AddressDTO> create(@RequestBody AddressDTO address, HttpSession session){
		Long userid = (Long) session.getAttribute("userId");
		User user = userRepository.findById(userid).orElseThrow();
		AddressDTO saved = addressService.create(address, user);
		return new ResponseEntity<>(saved, HttpStatus.OK);
	}
}
