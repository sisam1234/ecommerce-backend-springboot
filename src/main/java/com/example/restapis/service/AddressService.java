package com.example.restapis.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restapis.dto.AddressDTO;
import com.example.restapis.entity.Address;
import com.example.restapis.entity.User;
import com.example.restapis.repository.AddressRepository;
import com.example.restapis.repository.UserRepository;
import com.example.restapis.utils.AuthUtil;

@Service
public class AddressService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	AuthUtil authUtil;
	
	public AddressDTO create(AddressDTO addressdto) {
		Long userId = authUtil.loggedInUserId();
		User user =  userRepository.findById(userId).orElseThrow();
		Address address = modelMapper.map(addressdto, Address.class);
		address.setUser(user);
		Address saved = addressRepository.save(address);
		return modelMapper.map(saved, AddressDTO.class);
	}

}
