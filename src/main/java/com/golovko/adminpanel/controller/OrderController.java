package com.golovko.adminpanel.controller;

import com.golovko.adminpanel.dto.order.OrderPatchDTO;
import com.golovko.adminpanel.dto.order.OrderReadDTO;
import com.golovko.adminpanel.dto.order.OrderReadExtendedDTO;
import com.golovko.adminpanel.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/order-carts")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderReadDTO> getAllCustomerOrders(@PathVariable UUID customerId) {
        return orderService.getAllCustomerOrders(customerId);
    }

    @GetMapping("/{id}")
    public OrderReadExtendedDTO getExtendedOrder(
            @PathVariable UUID customerId,
            @PathVariable UUID id
    ) {
        return orderService.getExtendedOrder(customerId, id);
    }

    @PatchMapping("/{id}")
    public OrderReadExtendedDTO patchOrder(
            @PathVariable UUID customerId,
            @PathVariable UUID id,
            @RequestBody OrderPatchDTO patchDTO
    ) {
        return orderService.patchOrder(customerId, id, patchDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(
            @PathVariable UUID customerId,
            @PathVariable UUID id
    ) {
        orderService.deleteOrder(customerId, id);
    }
}
