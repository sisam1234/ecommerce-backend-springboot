package com.example.restapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapis.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
