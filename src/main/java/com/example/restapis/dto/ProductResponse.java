package com.example.restapis.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductResponse {

	private List<ProductDTO> content;

	public List<ProductDTO> getContent() {
		return content;
	}

	public void setContent(List<ProductDTO> content) {
		this.content = content;
	}
}
