package com.example.restapis.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LowStockConsumer {

    private static final Logger log =
        LoggerFactory.getLogger(LowStockConsumer.class);

    @KafkaListener(topics = "low-stock", groupId = "admin-alert-group")
    public void handleLowStock(String message) {
        log.warn("===========================================");
        log.warn("⚠️  LOW STOCK ALERT!");
        log.warn("    {}", message);
        log.warn("===========================================");
    }
}