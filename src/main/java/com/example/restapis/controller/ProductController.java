package com.example.restapis.controller;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.restapis.dto.ProductDTO;
import com.example.restapis.response.ProductResponse;
import com.example.restapis.service.FileService;
import com.example.restapis.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;




@RestController
public class ProductController {
	
	@Autowired
	private ProductService productSrevice;
	@Autowired
	FileService fileService;


	

	@PostMapping(value = "/admin/category/{categoryId}/product",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ProductDTO> addProduct(@RequestPart("product") ProductDTO request, @PathVariable Long categoryId, @RequestPart("image") MultipartFile image) throws IOException{
		 

		request.setImage(fileService.uploadImage(image));
		ProductDTO savedProduct = productSrevice.addProduct(request, categoryId);
		return new ResponseEntity<>(savedProduct, HttpStatus.OK);
		
		
		
	}
	@GetMapping("/products")
	public ResponseEntity<ProductResponse> getAllProduct(@RequestParam int number, @RequestParam int size){
		 ProductResponse res = productSrevice.getAllProduct(number,size);
		 return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
	
	@GetMapping("/category/{categoryId}/product")
	public ResponseEntity<ProductResponse> getProductByCategory(@PathVariable Long categoryId, @RequestParam int  number,@RequestParam int size){
	ProductResponse product = productSrevice.searchByCategory(categoryId, number,size);
	 return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@PutMapping("/admin/product/{id}")
	public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO request,@PathVariable Long id){
		ProductDTO product  = productSrevice.updateProduct(request, id);
		 return new ResponseEntity<>(product, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/admin/product/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		productSrevice.deleteProduct(id);
		return ResponseEntity.ok("deleted");
	}
	
}
