package com.example.restapis.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.restapis.dto.CartDTO;

import com.example.restapis.entity.Cart;

import com.example.restapis.repository.CartRepository;
import com.example.restapis.service.CartService;
import com.example.restapis.service.UserDetailsImpl;

import jakarta.servlet.http.HttpSession;

@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@PostMapping("product/{productId}/{quantity}")
		public ResponseEntity<?> addtocart(@PathVariable Long productId, @PathVariable int quantity, @AuthenticationPrincipal UserDetailsImpl user){
		try{
			
			Long userId = user.getId();
		CartDTO cart = cartService.addtocart(productId, quantity, userId);
			
			return new ResponseEntity<>(cart, HttpStatus.OK);
		}catch (RuntimeException e) {
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("Error: " + e.getMessage());
    }

			
		}
	@GetMapping("/cart/user")
	public ResponseEntity<CartDTO> getusercart( @AuthenticationPrincipal UserDetailsImpl user){
		
		Cart cart = cartRepository.findByUserId(user.getId());
		Long id = cart.getId();
		CartDTO carts = cartService.getUserCart(user.getId(), id);
		return new ResponseEntity<>(carts,HttpStatus.OK);
		 
	}
	
	@PostMapping("update/{productId}/{quantity}")
	public ResponseEntity<CartDTO> updateQuantity(@PathVariable Long productId, int quantity, @AuthenticationPrincipal UserDetailsImpl user){
		
		CartDTO  cart= cartService.updateProductQuantity(productId, quantity, user.getId());
				return new ResponseEntity<>(cart,HttpStatus.OK);
	}
	 @DeleteMapping("/cart/delete/{productId}")
	 public ResponseEntity<CartDTO> remeoveCartItem(@PathVariable Long productId,  @AuthenticationPrincipal UserDetailsImpl user){
		;
		 CartDTO  cart= cartService.removeCartItems(user.getId(), productId);
		 return new ResponseEntity<>(cart,HttpStatus.OK);
	 }
			
	
}


