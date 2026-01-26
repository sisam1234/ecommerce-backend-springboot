package com.example.restapis.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restapis.dto.OrderDTO;
import com.example.restapis.entity.Address;
import com.example.restapis.entity.Cart;
import com.example.restapis.entity.CartItem;
import com.example.restapis.entity.Order;
import com.example.restapis.entity.OrderItem;
import com.example.restapis.entity.Payment;
import com.example.restapis.repository.AddressRepository;
import com.example.restapis.repository.CartRepository;
import com.example.restapis.repository.OrderItemRepositotry;
import com.example.restapis.repository.OrderRepository;
import com.example.restapis.repository.PaymentRepository;

@Service
public class OrderService {
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderItemRepositotry orderItemRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	public OrderDTO placeOrder(String email, Long addressId,String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage) {
		Cart cart = cartRepository.findByEmail(email);
		Address address = addressRepository.findById(addressId).orElseThrow();

		Order order = new Order();
		order.setEmail(email);
		order.setCreatedAt(LocalDate.now());
		order.setAddress(address);
		order.setTotalAmount(cart.getTotalPrice());
		order.setOrderStatus("order accepted");
		Order saved = orderRepository.save(order);
		
		Payment payment = new Payment(paymentMethod, pgPaymentId, pgStatus, pgResponseMessage, pgName);
		payment.setOrder(order);
		paymentRepository.save(payment);
		
		
		List<OrderItem> orderItems = new ArrayList<>();
		List<CartItem> cartItems = cart.getCartItems();
		for(CartItem item:cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(saved);
			orderItem.setProducts(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setUnitPrice(item.getProduct().getPrice());
			orderItems.add(orderItem);
		}
		order.setOrderItems(orderItems);
		orderItems = orderItemRepository.saveAll(orderItems);
		
		Order savedorder = orderRepository.save(order);
		OrderDTO orderdto = modelMapper.map(savedorder, OrderDTO.class);
		return orderdto;
		
	}

}
