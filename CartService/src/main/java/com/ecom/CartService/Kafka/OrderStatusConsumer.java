package com.ecom.CartService.Kafka;

import com.ecom.CartService.Services.CartService;
import com.ecom.CartService.events.EventJson;
import com.ecom.CartService.events.OrderStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStatusConsumer {

    private static final String ORDER_STATUS_TOPIC = "order.status";

    private final CartService cartService;

    /**
      Empty the cart only once the order is CONFIRMED (stock reserved).
      If the order is CANCELLED the customer keeps the cart and can try again.
      Idempotent: clearing an already empty cart is harmless, so redelivery is fine.
     */
    @KafkaListener(topics = ORDER_STATUS_TOPIC)
    public void onOrderStatus(String payload) {
        OrderStatusEvent event = EventJson.fromJson(payload, OrderStatusEvent.class);
        if ("CONFIRMED".equals(event.status())) {
            cartService.clearCart(event.userId());
            log.info("Cart cleared for user {} after order {} was confirmed", event.userId(), event.orderId());
        }
    }
}
