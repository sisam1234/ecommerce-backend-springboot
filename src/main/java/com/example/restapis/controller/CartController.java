package com.example.restapis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapis.dto.CartDTO;
import com.example.restapis.dto.CartItemDTO;
import com.example.restapis.entity.Cart;
import com.example.restapis.entity.User;
import com.example.restapis.repository.CartRepository;
import com.example.restapis.service.CartService;

import jakarta.servlet.http.HttpSession;

@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@PostMapping("product/{productId}/{quantity}")
		public ResponseEntity<CartDTO> addtocart(@PathVariable Long productId, @PathVariable int quantity, HttpSession session){
		Long userId = (Long) session.getAttribute("userId");
		CartDTO cart = cartService.addtocart(productId, quantity, userId);
			
			return new ResponseEntity<>(cart, HttpStatus.OK);
			
			
		}
	@GetMapping("/cart/user")
	public ResponseEntity<CartDTO> getusercart(){
		Long userId = 7L;
		Cart cart = cartRepository.findByUserId(userId);
		Long id = cart.getId();
		CartDTO carts = cartService.getUserCart(userId, id);
		return new ResponseEntity<>(carts,HttpStatus.OK);
		 
	}
	
	@PostMapping("update/{productId}/{quantity}")
	public ResponseEntity<CartDTO> updateQuantity(@PathVariable Long productId, int quantity,HttpSession session){
		Long userId = (Long)session.getAttribute("userId");
		CartDTO  cart= cartService.updateProductQuantity(productId, quantity, userId);
				return new ResponseEntity<>(cart,HttpStatus.OK);
	}
	 @DeleteMapping("/cart/delete/{productId}")
	 public ResponseEntity<CartDTO> remeoveCartItem(@PathVariable Long productId, HttpSession session){
		 Long userId = (Long)session.getAttribute("userId");
		 CartDTO  cart= cartService.removeCartItems(userId, productId);
		 return new ResponseEntity<>(cart,HttpStatus.OK);
	 }
			
	
}


