package com.ecom.OrderService.Kafka;

import com.ecom.OrderService.Models.Order;
import com.ecom.OrderService.events.EventJson;
import com.ecom.OrderService.events.OrderCreatedEvent;
import com.ecom.OrderService.events.OrderStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishOrderCreated(Order order) {
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getUserId(),
                order.getItems().stream()
                        .map(i -> new OrderCreatedEvent.Item(i.getProductId(), i.getQuantity()))
                        .toList());
        send(Topics.ORDER_CREATED, order.getId(), EventJson.toJson(event));
    }

    public void publishOrderStatus(Order order) {
        OrderStatusEvent event = new OrderStatusEvent(order.getId(), order.getUserId(), order.getStatus().name());
        send(Topics.ORDER_STATUS, order.getId(), EventJson.toJson(event));
    }

    private void send(String topic, Long orderId, String json) {
        // key = orderId: every event of one order goes to the same partition, so they stay in order
        kafkaTemplate.send(topic, String.valueOf(orderId), json).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to publish to {} for order {}", topic, orderId, ex);
            }
        });
    }
}