package com.example.restapis.service;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;



@Service
public class KhaltiService {

   @Value("${khalti.secret-key}")
    private String secretKey;

    @Value("${khalti.initiate-url}")
    private String initiateUrl;

    @Value("${khalti.lookup-url}")
    private String lookupUrl;

    private final WebClient webClient = WebClient.builder().build();

    public Map<String, Object> initiatePayment(Long orderId, String userEmail, double amount ){
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> customer = new HashMap<>();
        customer.put("name", "Test User");
customer.put("email", userEmail);
customer.put("phone", "9800000000");
        payload.put("return_url", "http://localhost:8080/payment/callback");
        payload.put("website_url", "http://localhost:8080");
        payload.put("amount", (int)amount*100);
        payload.put("purchase_order_id", orderId);
        payload.put("customer_info", customer);
        payload.put("purchase_order_name", "Order #" + orderId);
        return webClient.post()
                .uri(initiateUrl)
                .header("Authorization", "Key " + secretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
    public Map<String, Object> verifyPayment(String pidx) {
        Map<String, String> payload = Map.of("pidx", pidx);

        return webClient.post()
                .uri(lookupUrl)
                .header("Authorization", "Key " + secretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}
