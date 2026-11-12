package com.ecom.OrderService.Kafka;

import com.ecom.OrderService.Services.OrderService;
import com.ecom.OrderService.events.EventJson;
import com.ecom.OrderService.events.InventoryResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryResultConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = Topics.INVENTORY_RESULT)
    public void onInventoryResult(String payload) {
        InventoryResultEvent event = EventJson.fromJson(payload, InventoryResultEvent.class);
        log.info("inventory.result received for order {} success={}", event.orderId(), event.success());
        orderService.handleInventoryResult(event);
    }
}