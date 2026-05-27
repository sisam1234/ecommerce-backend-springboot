package com.example.restapis.dto;

public class OrderItemDTO {
	private Long id;
	private ProductDTO products;
	private int quantity;
	private double unitPrice;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public ProductDTO getProducts() {
		return products;
	}
	public void setProducts(ProductDTO products) {
		this.products = products;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	

}
