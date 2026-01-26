package com.example.restapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapis.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
