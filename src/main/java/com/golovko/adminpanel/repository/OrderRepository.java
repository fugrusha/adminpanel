package com.golovko.adminpanel.repository;

import com.golovko.adminpanel.domain.OrderCart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<OrderCart, UUID> {

    OrderCart findByIdAndCustomerId(UUID id, UUID customerId);

    List<OrderCart> findByCustomerId(UUID customerId);

    @Query("from OrderCart")
    List<OrderCart> getAllOrders();
}
