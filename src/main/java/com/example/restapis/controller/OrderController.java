package com.example.restapis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<OrderDTO> placeorder( @PathVariable Long addressId,@RequestBody OrderRequest request){
	System.out.println("hello");
		OrderDTO order = orderService.placeOrder(
				addressId,
				request.getPaymentMethod(),
				request.getPgName(),
				request.getPgPaymentId(),
				request.getPgstatus(),
				request.getPgResponseMessage()
				);
				System.out.println("bye");
		return new ResponseEntity<OrderDTO>(order, HttpStatus.OK);
	}
}
