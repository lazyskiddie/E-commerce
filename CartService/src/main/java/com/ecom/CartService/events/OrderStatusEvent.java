package com.ecom.CartService.events;

// Consumed from topic "order.status" (published by OrderService). status = CONFIRMED / CANCELLED.

public record OrderStatusEvent(Long orderId, String userId, String status) {
}
