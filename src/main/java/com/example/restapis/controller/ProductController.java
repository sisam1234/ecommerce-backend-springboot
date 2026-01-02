package com.example.restapis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapis.dto.Product;
import com.example.restapis.repository.ProductRepository;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping("/product")
	public List<Product> getProduct(){
		return productRepo.findAll();
		
	}
	
	@PostMapping("/add")
	 public String addProduct(@RequestBody Product product) {
		 productRepo.save(product);
		 return "Product added successfull";
	 }
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<Product> getById(@PathVariable int id){
		return productRepo.findById(id).map(ResponseEntity::ok)              
                .orElse(ResponseEntity.notFound().build());
		
	}
	@GetMapping("/getbyname")
	public List<Product> getByName(@RequestParam String des ){
		return productRepo.findByDescription(des);
	}
	
	
}
