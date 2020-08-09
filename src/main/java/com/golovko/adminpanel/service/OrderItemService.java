package com.golovko.adminpanel.service;

import com.golovko.adminpanel.dto.order.OrderReadExtendedDTO;

import java.util.UUID;

public interface OrderItemService {

    OrderReadExtendedDTO addProductToOrder(UUID orderId, UUID productId);

    OrderReadExtendedDTO removeProductFromOrder(UUID orderId, UUID productId);
}
