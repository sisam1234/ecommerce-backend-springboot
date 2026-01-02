package com.example.restapis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapis.dto.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	public List<Product> findByName(String name);
	public List<Product> findByDescription(String des);
	
}
