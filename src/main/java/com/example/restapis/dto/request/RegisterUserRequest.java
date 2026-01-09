package com.example.restapis.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterUserRequest {

	@NotBlank(message = "Your name is required")
	private String name;
	@NotBlank(message = "Your email is requried")
	@Email(message = "Email must be valid")
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password should contain at least 6 characters")
	private String password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
