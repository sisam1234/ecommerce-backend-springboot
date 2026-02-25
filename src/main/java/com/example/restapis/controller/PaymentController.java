package com.example.restapis.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapis.entity.Payment;
import com.example.restapis.repository.PaymentRepository;
import com.example.restapis.service.KhaltiService;

@RestController
public class PaymentController {
    @Autowired 
    KhaltiService khaltiService;
@Autowired
PaymentRepository paymentRepository;

    @GetMapping("/payment/callback")
    public String khaltiCallback(@RequestParam String pidx){
        Map<String, Object> verify = khaltiService.verifyPayment(pidx);
        String status =  (String) verify.get("status");
        Payment payment = paymentRepository.findByPaymentId(pidx);
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
     return "Payment Successful";
    }
}
