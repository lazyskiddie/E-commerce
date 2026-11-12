package com.ecom.OrderService.Kafka;

public final class Topics {
    public static final String ORDER_CREATED = "order.created";
    public static final String ORDER_STATUS = "order.status";
    public static final String INVENTORY_RESULT = "inventory.result";

    private Topics() {
    }
}