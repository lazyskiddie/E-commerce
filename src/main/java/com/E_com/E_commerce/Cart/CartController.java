package com.E_com.E_commerce.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId, @RequestBody CartItemRequest request) {
        if(!cartService.addToCart(userId, request)){
            return ResponseEntity.badRequest().body("Something went Wrong!!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
