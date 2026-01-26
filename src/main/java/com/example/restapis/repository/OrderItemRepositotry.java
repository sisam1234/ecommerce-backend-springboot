package com.example.restapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapis.entity.OrderItem;

public interface OrderItemRepositotry extends JpaRepository<OrderItem, Long>{

}
