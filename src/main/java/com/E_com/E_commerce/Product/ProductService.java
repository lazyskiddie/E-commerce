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

    private ProductResponse mapToProductResponse(Product saveProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(saveProduct.getId());
        response.setName(saveProduct.getName());
        response.setPrice(saveProduct.getPrice());
        response.setDescription(saveProduct.getDescription());
        response.setCategory(saveProduct.getCategory());
        response.setBlockQuantity(saveProduct.getBlockQuantity());
        response.setImageurl(saveProduct.getImageurl());
        response.setActive(saveProduct.getActive());
        return response;
    }

}
