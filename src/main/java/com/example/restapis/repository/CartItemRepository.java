package com.example.restapis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.restapis.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	
	Optional<CartItem> findByCartIdAndProductId(Long cartId,Long productId);
	
}
