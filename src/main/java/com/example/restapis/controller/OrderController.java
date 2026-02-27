package com.example.restapis.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapis.dto.OrderDTO;
import com.example.restapis.dto.OrderRequest;
import com.example.restapis.entity.User;
import com.example.restapis.repository.UserRepository;
import com.example.restapis.service.OrderService;

import jakarta.servlet.http.HttpSession;

@RestController
public class OrderController {
	 @Autowired
	 UserRepository userRepo;
	 
	 @Autowired
	 OrderService orderService;

	 @PostMapping("/placeorder/{addressId}")
    public ResponseEntity<Map<String,Object>> placeOrder(@PathVariable Long addressId,@RequestParam String paymentMethod) {
        Map<String,Object> response = orderService.placeOrder(addressId,paymentMethod);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
