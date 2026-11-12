package com.ecom.OrderService.events;

import java.util.List;

// Published to topic "order.created" (consumed by ProductService to reserve stock).
public record OrderCreatedEvent(Long orderId, String userId, List<Item> items) {

    public record Item(String productId, Integer quantity) {
    }
}
