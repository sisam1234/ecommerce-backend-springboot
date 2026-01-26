package com.example.restapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapis.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
