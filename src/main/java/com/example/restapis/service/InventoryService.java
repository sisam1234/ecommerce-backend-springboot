package com.example.restapis.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import com.example.restapis.dto.OrderDTO;
import com.example.restapis.dto.ProductDTO;
import com.example.restapis.entity.Order;
import com.example.restapis.entity.OrderItem;
import com.example.restapis.entity.Product;
import com.example.restapis.repository.ProductRepository;

@Service
public class InventoryService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    KafkaTemplate  kafkaTemplate;

    public boolean isInStock(Long productId, int quantity){
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("product not found"));
        return product.getStockQuantity()>=quantity;
    }
    public ProductDTO addStock(Long productId, int quantity){
        Product product = productRepository.findById(productId).orElseThrow();
        product.setStockQuantity(product.getStockQuantity()+quantity);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }
    @KafkaListener(topics = "order-events",groupId = "inventory-group")
    public void hanbleOrderPlaced(OrderDTO orderdto){
        Order order = modelMapper.map(orderdto, Order.class);
       for(OrderItem  item : order.getOrderItems()){
        reduceStock(item.getProducts().getId(),item.getQuantity());
       

       }
    
      
    }
    
    public void reduceStock(Long  productId, int quantity){
        Product product = productRepository.findById(productId).orElseThrow();
        product.setStockQuantity(product.getStockQuantity()-quantity);
        productRepository.save(product);
         if (product.getStockQuantity() < 15) {
        String alert = "ProductId:" + product.getId()
            + ",ProductName:" + product.getName()
            + ",RemainingStock:" + product.getStockQuantity();
            kafkaTemplate.send("low-stock", alert);
      }
    }
}
