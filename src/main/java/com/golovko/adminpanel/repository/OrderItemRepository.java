package com.golovko.adminpanel.repository;

import com.golovko.adminpanel.domain.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, UUID> {
}
