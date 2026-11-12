package com.ecom.OrderService.events;

// Published to topic "order.status" (consumed by CartService, and later e.g. notifications).
public record OrderStatusEvent(Long orderId, String userId, String status) {
}
