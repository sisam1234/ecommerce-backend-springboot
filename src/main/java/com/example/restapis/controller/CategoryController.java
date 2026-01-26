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

import com.example.restapis.dto.CategoryDTO;
import com.example.restapis.dto.CategoryResponse;
import com.example.restapis.entity.Category;
import com.example.restapis.service.CategoryService;

@RestController
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	@PostMapping("/category")
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO request){
		CategoryDTO saved = categoryService.createCategory(request);
		return new ResponseEntity<>(saved,HttpStatus.OK);
	}
	 @GetMapping("/category")
	 public CategoryResponse getCategories(){
		CategoryResponse cat = categoryService.getAllCategory();
		 return cat;
		 
	 }
	 @DeleteMapping("/delete/category/{categoryId}")
	 public ResponseEntity<?> delete(@PathVariable Long categoryId){
		 categoryService.deleteCategory(categoryId);
		 return ResponseEntity.ok("deleted");
	 }
	 
	 @PutMapping("/update/category/{categoryId}")
	 public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO request, @PathVariable Long categoryId){
		 CategoryDTO cat = categoryService.updateCategory(request, categoryId);
		 return new ResponseEntity<>(cat,HttpStatus.OK);
	 }
}
