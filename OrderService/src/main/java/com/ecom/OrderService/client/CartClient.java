package com.ecom.OrderService.client;

import com.ecom.OrderService.dto.CartItemDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class CartClient {

    private final RestClient restClient;

    public CartClient(RestClient cartRestClient) {
        this.restClient = cartRestClient;
    }

    public List<CartItemDTO> getCart(String userId) {
        return restClient.get()
                .uri("/api/cart")
                .header("X-User-ID", userId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<CartItemDTO>>() {});
    }
}