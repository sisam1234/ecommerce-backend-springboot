package com.example.restapis.entity;

import java.time.LocalDate;



import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile {

	@Id
	
	private Long id;
	private String phone;
	private LocalDate dateOfBirth;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	@MapsId
	private User user;
	
	


}
