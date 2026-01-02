package com.example.restapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapis.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	
}
