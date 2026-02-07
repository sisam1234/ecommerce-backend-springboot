package com.example.restapis.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapis.dto.ApiResponse;
import com.example.restapis.dto.CartDTO;

import com.example.restapis.entity.Cart;

import com.example.restapis.repository.CartRepository;
import com.example.restapis.service.CartService;
import com.example.restapis.service.UserDetailsImpl;



@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired

	
	@PostMapping("/cart")
		public ResponseEntity<ApiResponse<CartDTO>> addtocart(@RequestParam Long productId, @RequestParam int quantity){
		try{
			
			
		CartDTO cart = cartService.addtocart(productId, quantity);
			ApiResponse<CartDTO> apiResponse = new ApiResponse<>("Added to cart", true, cart);
			return ResponseEntity.ok(apiResponse);
		}catch (Exception e){
			ApiResponse<CartDTO> apiResponse = new ApiResponse<>(e.getMessage(), false, null);
			return ResponseEntity.badRequest().body(apiResponse);
		}
	
			
		}
	@GetMapping("/cart")
	public ResponseEntity<CartDTO> getusercart(){
		
		
		CartDTO carts = cartService.getUserCart();
		return new ResponseEntity<>(carts,HttpStatus.OK);
		 
	}
	
	@PutMapping("cart/{itemId}")
	public ResponseEntity<CartDTO> updateQuantity(@PathVariable Long itemId,@RequestParam int quantity){
		
		CartDTO  cart= cartService.updateProductQuantity(itemId,quantity);
				return new ResponseEntity<>(cart,HttpStatus.OK);
	}
	 @DeleteMapping("/cart/{productId}")
	 public ResponseEntity<CartDTO> remeoveCartItem(@PathVariable Long productId,  @AuthenticationPrincipal UserDetailsImpl user){
		;
		 CartDTO  cart= cartService.removeCartItems(user.getId(), productId);
		 return new ResponseEntity<>(cart,HttpStatus.OK);
	 }
			
	
}


