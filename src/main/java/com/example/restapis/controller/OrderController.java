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
	public ResponseEntity<OrderDTO> placeorder( @PathVariable Long addressId,@RequestBody OrderRequest request, HttpSession session){
		Long userId = (Long)  session.getAttribute("userId");
		if (userId == null) {
		    throw new RuntimeException("User not logged in or session expired");
		}
		
		User user = userRepo.findById(userId).orElseThrow(()->  new RuntimeException("user not found"));
		String email = user.getEmail();
		OrderDTO order = orderService.placeOrder(
				email,
				addressId,
				request.getPaymentMethod(),
				request.getPgName(),
				request.getPgPaymentId(),
				request.getPgstatus(),
				request.getPgResponseMessage()
				);
		return new ResponseEntity<OrderDTO>(order, HttpStatus.OK);
	}
}
