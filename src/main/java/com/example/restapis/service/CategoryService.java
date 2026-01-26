package com.example.restapis.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restapis.dto.CategoryDTO;
import com.example.restapis.dto.CategoryResponse;
import com.example.restapis.entity.Category;
import com.example.restapis.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	ModelMapper modelMapper;
	
	public CategoryDTO createCategory(CategoryDTO request) {
		Category cat  = modelMapper.map(request, Category.class);
		Category saved = categoryRepo.save(cat);
		return modelMapper.map(saved, CategoryDTO.class);
	}
	public CategoryResponse getAllCategory() {
		List<Category> cat  = categoryRepo.findAll();
		if(cat.isEmpty()) {
			throw new RuntimeException("No category created till now");
		}
		List<CategoryDTO> catDTO= cat.stream()
			    .map(c -> modelMapper.map(c, CategoryDTO.class))
			    .collect(Collectors.toList());
	CategoryResponse  catResponse = new CategoryResponse();
	catResponse.setContent(catDTO);
	
	return catResponse;
		
	}
	public void deleteCategory(Long categoryId) {
		Category cat = categoryRepo.findById(categoryId).orElseThrow(()->new RuntimeException("Category not found"));
		categoryRepo.delete(cat);
	}
	public CategoryDTO updateCategory(CategoryDTO request, Long categoryId) {
		Category savedcat = categoryRepo.findById(categoryId).orElseThrow(()-> new RuntimeException("not found"));
		savedcat.setName(request.getName());
		categoryRepo.save(savedcat);
		return modelMapper.map(savedcat, CategoryDTO.class);
	}
}
