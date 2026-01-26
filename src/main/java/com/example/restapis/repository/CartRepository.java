package com.example.restapis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.restapis.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUserId(Long userId);
	Optional<Cart> findByUserIdAndId(Long userId, Long id);
	@Query("select c from Cart c where c.user.email=:email")
	Cart findByEmail(@Param("email") String email); 
}
