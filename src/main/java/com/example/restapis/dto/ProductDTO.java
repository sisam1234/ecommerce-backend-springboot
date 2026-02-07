package com.example.restapis.dto;

public class ProductDTO {
	
	private Long id;
	private String name;
	private String description;
	private float price;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	private String image;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
}
