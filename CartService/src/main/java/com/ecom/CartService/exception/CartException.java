package com.ecom.CartService.exception;

// Business rule problem while adding to the cart (unknown product, not enough stock, ...)
public class CartException extends RuntimeException {
    public CartException(String message) {
        super(message);
    }
}