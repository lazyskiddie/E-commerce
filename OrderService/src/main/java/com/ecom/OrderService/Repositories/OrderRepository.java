package com.ecom.OrderService.Repositories;

import com.ecom.OrderService.Models.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // items are fetched together with the order: open-in-view is off, so lazy loading
    // after the query would throw LazyInitializationException when building the response
    @EntityGraph(attributePaths = "items")
    Optional<Order> findByIdAndUserId(Long id, String userId);

    @EntityGraph(attributePaths = "items")
    List<Order> findByUserIdOrderByCreatedAtDesc(String userId);
}
