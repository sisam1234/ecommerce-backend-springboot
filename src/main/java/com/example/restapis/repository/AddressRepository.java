package com.example.restapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapis.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
