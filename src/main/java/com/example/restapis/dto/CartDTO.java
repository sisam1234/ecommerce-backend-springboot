package com.example.restapis.dto;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {
	private Long id;
	private double totalPrice = 0.0;
	private List<ProductDTO> products  =  new ArrayList<>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<ProductDTO> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
	
	

}
