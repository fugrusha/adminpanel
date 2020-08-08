package com.golovko.adminpanel.service.impl;

import com.golovko.adminpanel.dto.order.OrderPatchDTO;
import com.golovko.adminpanel.dto.order.OrderReadDTO;
import com.golovko.adminpanel.dto.order.OrderReadExtendedDTO;
import com.golovko.adminpanel.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public OrderReadExtendedDTO getExtendedOrder(UUID customerId, UUID id) {
        return null;
    }

    @Override
    public List<OrderReadDTO> getAllCustomerOrders(UUID customerId) {
        return null;
    }

    @Override
    public OrderReadExtendedDTO patchOrder(UUID customerId, UUID id, OrderPatchDTO patchDTO) {
        return null;
    }

    @Override
    public void deleteOrder(UUID customerId, UUID id) {

    }

    @Override
    public OrderReadExtendedDTO addProductToOrder(UUID customerId, UUID orderId, UUID productId) {
        return null;
    }

    @Override
    public OrderReadExtendedDTO removeProductFromOrder(UUID customerId, UUID orderId, UUID productId) {
        return null;
    }

    @Override
    public List<OrderReadDTO> getAllOrders() {
        return null;
    }
}
