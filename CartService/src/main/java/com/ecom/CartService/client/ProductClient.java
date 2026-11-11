package com.ecom.CartService.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(RestClient productRestClient) {
        this.restClient = productRestClient;
    }

    // Asks ProductService for the real product (price + stock). Never trust a price sent by the browser.
    public ProductDTO getProduct(String productId) {
        return restClient.get()
                .uri("/api/product/{id}", productId)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        (req, res) -> { throw new CartException("Product " + productId + " does not exist"); })
                .body(ProductDTO.class);
    }
}
