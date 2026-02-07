package com.example.restapis.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


import com.example.restapis.dto.CartDTO;
import com.example.restapis.dto.ProductDTO;
import com.example.restapis.entity.Cart;
import com.example.restapis.entity.CartItem;
import com.example.restapis.entity.Product;
import com.example.restapis.entity.User;
import com.example.restapis.repository.CartItemRepository;
import com.example.restapis.repository.CartRepository;
import com.example.restapis.repository.ProductRepository;
import com.example.restapis.repository.UserRepository;
import com.example.restapis.utils.AuthUtil;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	AuthUtil authUtil;
	
	private Cart createCart(Long userId) {
		Cart userCart = cartRepository.findByUserId(userId);
		User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("user not found"));
		if(userCart!=null) {
			return userCart;
		}
		Cart cart = new Cart();
		cart.setUser(user);
		cart.setTotalPrice(0.0);
		Cart newCart  = cartRepository.save(cart);
		return newCart;
		
	}
	public CartDTO addtocart(Long productId, int quantity) {
		Long userId = authUtil.loggedInUserId();
		Cart cart = createCart(userId);
		Optional<CartItem> existingItems = cartItemRepository.findByCartIdAndProductId(cart.getId(),productId);
		if(existingItems.isPresent()) {
			CartItem item = existingItems.get();
			item.setQuantity(quantity+item.getQuantity());
			cartItemRepository.save(item);
		}
		else {
			CartItem cartItem = new CartItem();
			Product product = productRepository.findById(productId).orElseThrow();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(quantity);
			cart.getCartItems().add(cartItem);
			cartItemRepository.save(cartItem);
		}
		double totalPrice   = 0.0;
		for(CartItem items: cart.getCartItems()) {
			totalPrice+=items.getProduct().getPrice()*items.getQuantity();
		}
		cart.setTotalPrice(totalPrice);
		cartRepository.save(cart);
		CartDTO cartdto = modelMapper.map(cart, CartDTO.class);
		List<ProductDTO> product  = cart.getCartItems().stream().map(item->{
			ProductDTO productdto = modelMapper.map(item.getProduct(), ProductDTO.class);
			
			return productdto;
		}).collect(Collectors.toList());
		cartdto.setProducts(product);
		
		return cartdto;
	}
	public CartDTO getUserCart(){
		Long userId = authUtil.loggedInUserId();
		Cart cart= cartRepository.findByUserId(userId);
	
		if(cart ==  null) {
			throw new RuntimeException("nothing in cart");
	
		}
		CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
		List<CartItem> cartItems= cart.getCartItems();
		List<ProductDTO> products = cartItems.stream().map(item->{
			ProductDTO dto = modelMapper.map(item.getProduct(), ProductDTO.class);
			return dto;
		}).collect(Collectors.toList());
		cartDTO.setProducts(products);
		return cartDTO;
	}
	
	public CartDTO updateProductQuantity(Long itemId, int quantity) {
		Long userId = authUtil.loggedInUserId();
		CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow();
		if(userId !=cartItem.getCart().getUser().getId()){
			throw new AccessDeniedException("not authorized");
		}
		cartItem.setQuantity(quantity);
		cartItemRepository.save(cartItem);
		Cart cart = cartItem.getCart();
		 double totalPrice = 0.0;
		    for (CartItem item : cart.getCartItems()) {
		        totalPrice += item.getProduct().getPrice() * item.getQuantity();
		    }

		    cart.setTotalPrice(totalPrice);
			cartRepository.save(cart);
			

		CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
		List<CartItem> cartItems= cart.getCartItems();
		List<ProductDTO> products = cartItems.stream().map(item->{
			ProductDTO dto = modelMapper.map(item.getProduct(), ProductDTO.class);
			return dto;
		}).collect(Collectors.toList());
		cartDTO.setProducts(products);
		return cartDTO;
		
	}
	public CartDTO removeCartItems(Long userId, Long productId) {
		Cart cart  = cartRepository.findByUserId(userId);
		CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId).orElseThrow();
		cartItemRepository.delete(cartItem);
		cart.setTotalPrice(cart.getTotalPrice()-cartItem.getQuantity()*cartItem.getProduct().getPrice());
		cartRepository.save(cart);
		CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
		List<CartItem> cartItems= cart.getCartItems();
		List<ProductDTO> products = cartItems.stream().map(item->{
			ProductDTO dto = modelMapper.map(item.getProduct(), ProductDTO.class);
			return dto;
		}).collect(Collectors.toList());
		cartDTO.setProducts(products);
		return cartDTO;
		
		
	}
}
