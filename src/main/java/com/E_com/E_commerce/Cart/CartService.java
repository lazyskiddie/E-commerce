package com.E_com.E_commerce.Cart;

import com.E_com.E_commerce.Cart.dto.CartItemRequest;
import com.E_com.E_commerce.Product.Product;
import com.E_com.E_commerce.Product.ProductRepository;
import com.E_com.E_commerce.User.User;
import com.E_com.E_commerce.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOptional = productRepository.findById(request.getProductId());
        if(productOptional.isEmpty())
            return false;

        Product product = productOptional.get();
        if(Integer.parseInt(product.getBlockQuantity()) < request.getQuantity())
            return false;

        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if(userOptional.isEmpty())
            return false;
        User user = userOptional.get();

        Cart exestingCartItem = cartRepository.findByUserAndProduct(user, product);
        if(exestingCartItem != null){
            exestingCartItem.setQuantity(exestingCartItem.getQuantity() + request.getQuantity());
            exestingCartItem.setPrice(new BigDecimal(product.getPrice()).multiply(BigDecimal.valueOf(exestingCartItem.getQuantity())));
            cartRepository.save(exestingCartItem);
        } else {
            Cart newCartItem = new Cart();
            newCartItem.setUser(user);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(request.getQuantity());
            newCartItem.setPrice(new BigDecimal(product.getPrice()).multiply(BigDecimal.valueOf(request.getQuantity())));
            cartRepository.save(newCartItem);
        }
        return true;
    }
}
