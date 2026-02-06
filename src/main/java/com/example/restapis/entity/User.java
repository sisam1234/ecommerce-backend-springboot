package com.example.restapis.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


	@Entity
	@Table(name="users")

	public class User {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		private String name;
		private String email;
		private String password;
		@Enumerated(value = EnumType.STRING)
		@Column(nullable = false)
		private Role role;
		
		
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
		
		@OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST, CascadeType.REMOVE},orphanRemoval = true )
		private List<Address> addresses = new ArrayList<>();
		public Role getRole() {
			return role;
		}
		public void setRole(Role role) {
			this.role = role;
		}
		public void addAddress(Address address) {
			addresses.add(address);
			address.setUser(this);
		}
		public void removeAddress(Address address) {
			addresses.remove(address);
			address.setUser(null);
		}
		@OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
	    private Profile profile;
		
	
		
		
 		
		 @OneToOne(mappedBy = "user")
		 private Cart cart;


		 public long getId() {
			 return id;
		 }
		 public void setId(long id) {
			 this.id = id;
		 }
		 public List<Address> getAddresses() {
			 return addresses;
		 }
		 public void setAddresses(List<Address> addresses) {
			 this.addresses = addresses;
		 }
		 public Profile getProfile() {
			 return profile;
		 }
		 public void setProfile(Profile profile) {
			 this.profile = profile;
		 }
		 
		 public Cart getCart() {
			 return cart;
		 }
		 public void setCart(Cart cart) {
			 this.cart = cart;
		 }
}
