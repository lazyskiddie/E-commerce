package com.E_com.E_commerce.Product;

import com.E_com.E_commerce.Product.dto.ProductRequest;
import com.E_com.E_commerce.Product.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product  product = new Product();
        UpdateProductFromrequest(product, productRequest);
        Product saveProduct = productRepository.save(product);
        return mapToProductResponse(saveProduct);
    }

}
