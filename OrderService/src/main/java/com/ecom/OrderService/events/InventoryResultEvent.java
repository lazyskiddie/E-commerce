package com.ecom.OrderService.events;

public record InventoryResultEvent(Long orderId, String userId, boolean success, String reason) {
}
