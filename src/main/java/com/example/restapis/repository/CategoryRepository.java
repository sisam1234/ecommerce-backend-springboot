package com.example.restapis.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapis.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
