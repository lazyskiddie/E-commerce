package com.ecom.CartService.Services;

import com.ecom.CartService.Models.Cart;
import com.ecom.CartService.Repositories.CartRepository;
import com.ecom.CartService.dto.CartItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        Cart existingCartItem = cartRepository.findByUserIdAndProductId(userId, request.getProductId());

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartRepository.save(existingCartItem);
        } else {
            Cart cartItem = new Cart();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, String productId) {
        Cart cartItem = cartRepository.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {
            cartRepository.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<Cart> getCart(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartRepository.deleteByUserId(userId);
    }
}
