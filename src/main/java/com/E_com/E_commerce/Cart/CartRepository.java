package com.E_com.E_commerce.Cart;

import com.E_com.E_commerce.Product.Product;
import com.E_com.E_commerce.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserAndProduct(User user, Product product);
    void deleteByUserAndProduct(User user, Product product);
}
