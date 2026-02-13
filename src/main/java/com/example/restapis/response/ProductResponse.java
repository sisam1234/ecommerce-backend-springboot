package com.example.restapis.response;

import java.util.List;

import com.example.restapis.dto.ProductDTO;


public class ProductResponse {

	private List<ProductDTO> content;
	private int totalPages;
	private int totalElements;
	private int size;
	private int number;
	private boolean first;
	private boolean last;

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public List<ProductDTO> getContent() {
		return content;
	}

	public void setContent(List<ProductDTO> content) {
		this.content = content;
	}
}
