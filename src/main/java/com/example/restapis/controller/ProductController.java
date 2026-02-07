package com.example.restapis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapis.dto.ProductDTO;
import com.example.restapis.dto.ProductResponse;

import com.example.restapis.service.ProductService;


@RestController
public class ProductController {
	
	@Autowired
	private ProductService productSrevice;

	@PostMapping("/admin/category/{categoryId}/product")
	public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO request, @PathVariable Long categoryId){
	
		ProductDTO savedProduct = productSrevice.addProduct(request, categoryId);
		return new ResponseEntity<>(savedProduct, HttpStatus.OK);
		
		
		
	}
	@GetMapping("/products")
	public ResponseEntity<ProductResponse> getAllProduct(){
		 ProductResponse res = productSrevice.getProduct();
		 return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
	
	@GetMapping("/category/{categoryId}/product")
	public ResponseEntity<ProductResponse> getProductByCategory(@PathVariable Long categoryId){
	ProductResponse product = productSrevice.searchByCategory(categoryId);
	 return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO request,@PathVariable Long id){
		ProductDTO product  = productSrevice.updateProduct(request, id);
		 return new ResponseEntity<>(product, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		productSrevice.deleteProduct(id);
		return ResponseEntity.ok("deleted");
	}
	
}
