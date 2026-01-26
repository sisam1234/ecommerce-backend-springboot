package com.example.restapis.service;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restapis.dto.ProductDTO;
import com.example.restapis.dto.ProductResponse;
import com.example.restapis.entity.Category;
import com.example.restapis.entity.Product;
import com.example.restapis.repository.CategoryRepository;
import com.example.restapis.repository.ProductRepository;

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
	public ProductResponse getProduct(){
		List<Product> products = productRepo.findAll();
		List<ProductDTO> productDTO = products.stream().map(p->modelMapper.map(p, ProductDTO.class)).collect(Collectors.toList());
		ProductResponse productResponse = new ProductResponse();
		 productResponse.setContent(productDTO);
		 return productResponse;
	}
	public ProductResponse searchByCategory(Long categoryId){
		Category cat = categoryRepo.findById(categoryId).orElseThrow();
		List<Product> products = cat.getProducts();
		List<ProductDTO> savedproducts = products.stream().map(p->modelMapper.map(p, ProductDTO.class)).collect(Collectors.toList());
		ProductResponse res = new ProductResponse();
		res.setContent(savedproducts);
		return res;
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
