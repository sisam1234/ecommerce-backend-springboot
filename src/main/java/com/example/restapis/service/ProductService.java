package com.example.restapis.service;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.restapis.dto.ProductDTO;
import com.example.restapis.entity.Category;
import com.example.restapis.entity.Product;
import com.example.restapis.repository.CategoryRepository;
import com.example.restapis.repository.ProductRepository;
import com.example.restapis.response.ProductResponse;

@Service
public class ProductService {
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	ModelMapper modelMapper;
		public ProductDTO addProduct(ProductDTO request,Long categoryId) {
		Category cat = categoryRepo.findById(categoryId).orElseThrow(()-> new RuntimeException("Category not found"));
		boolean isprodutFound = true;
		List<Product> products = cat.getProducts();
		for(Product product: products) {
			if(product.getName().equals(request.getName())) {
				isprodutFound = false;
				break;
			}
		}
		if(isprodutFound) {
			Product product = modelMapper.map(request, Product.class);
			product.setCategory(cat);
			Product savedProduct = productRepo.save(product);
			return modelMapper.map(savedProduct, ProductDTO.class);
		}
		else {
			throw new RuntimeException("already exist");
		}
		
		
	}
	public ProductResponse getAllProduct(int number, int size){
		Pageable pageDetails = PageRequest.of(number, size);
		Page<Product> productpage = productRepo.findAll(pageDetails);
		List<Product> products = productpage.getContent();
		List<ProductDTO> productDTO = products.stream().map(p->modelMapper.map(p, ProductDTO.class)).collect(Collectors.toList());
		ProductResponse productResponse = new ProductResponse();
		 productResponse.setContent(productDTO);
		 productResponse.setNumber(number);
		 productResponse.setSize(size);
		 productResponse.setTotalPages(productpage.getTotalPages());
		 productResponse.setTotalElements(productpage.getNumberOfElements());
		 productResponse.setFirst(productpage.isFirst());
		 productResponse.setLast(productpage.isLast());
		 return productResponse;
	}
	public ProductResponse searchByCategory(Long categoryId, int number, int size){
		Pageable pageDetails =PageRequest.of(number, size);
		Page<Product> productpage = productRepo.findByCategoryId(categoryId,pageDetails);
		List<Product> products = productpage.getContent();
		List<ProductDTO> productDTO = products.stream().map(p->modelMapper.map(p, ProductDTO.class)).collect(Collectors.toList());
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productDTO);
		 productResponse.setNumber(number);
		 productResponse.setSize(size);
		 productResponse.setTotalPages(productpage.getTotalPages());
		 productResponse.setTotalElements(productpage.getNumberOfElements());
		 productResponse.setFirst(productpage.isFirst());
		 productResponse.setLast(productpage.isLast());
		 return productResponse;
	}
	
	public ProductDTO updateProduct(ProductDTO request, Long id) {
		Product productFromDB = productRepo.findById(id).orElseThrow();
		Product product = modelMapper.map(request, Product.class);
		productFromDB.setName(product.getName());
		productFromDB.setDescription(product.getDescription());
		productFromDB.setPrice(request.getPrice());
		Product savedProduct = productRepo.save(productFromDB);
		return modelMapper.map(savedProduct, ProductDTO.class);
		
	}
	public void deleteProduct(Long id) {
		Product product = productRepo.findById(id).orElseThrow();
		productRepo.delete(product);
		
	}

}
