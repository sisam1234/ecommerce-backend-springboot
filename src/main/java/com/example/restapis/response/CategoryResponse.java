package com.example.restapis.response;

import java.util.List;

import com.example.restapis.dto.CategoryDTO;


public class CategoryResponse {
	private List<CategoryDTO> content;

	public List<CategoryDTO> getContent() {
		return content;
	}

	public void setContent(List<CategoryDTO> content) {
		this.content = content;
	}

}
