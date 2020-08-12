package com.golovko.adminpanel.service;

import com.golovko.adminpanel.dto.order.OrderFilter;
import com.golovko.adminpanel.dto.order.OrderPatchDTO;
import com.golovko.adminpanel.dto.order.OrderReadDTO;
import com.golovko.adminpanel.dto.order.OrderReadExtendedDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderReadExtendedDTO getExtendedOrder(UUID customerId, UUID id);

    List<OrderReadDTO> getAllCustomerOrders(UUID customerId);

    OrderReadExtendedDTO patchOrder(UUID customerId, UUID id, OrderPatchDTO patchDTO);

    void deleteOrder(UUID customerId, UUID id);

    List<OrderReadDTO> getAllOrders(OrderFilter filter);
}
