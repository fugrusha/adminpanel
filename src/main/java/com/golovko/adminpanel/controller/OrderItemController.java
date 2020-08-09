package com.golovko.adminpanel.controller;

import com.golovko.adminpanel.dto.order.OrderReadExtendedDTO;
import com.golovko.adminpanel.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order-carts")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/{id}/products/{productId}")
    public OrderReadExtendedDTO addProductToOrder(
            @PathVariable UUID id,
            @PathVariable UUID productId
    ) {
        return orderItemService.addProductToOrder(id, productId);
    }

    @DeleteMapping("/{id}/products/{productId}")
    public OrderReadExtendedDTO removeProductFromOrder(
            @PathVariable UUID id,
            @PathVariable UUID productId
    ) {
        return orderItemService.removeProductFromOrder(id, productId);
    }
}
