package com.example.restapis.repository;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapis.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    Page<Product> findAll(Pageable pageable);

    Page<Product>findByCategoryId(Long categoryId, Pageable pageable);
}
