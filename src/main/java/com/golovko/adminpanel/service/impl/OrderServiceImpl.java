package com.golovko.adminpanel.service.impl;

import com.golovko.adminpanel.domain.OrderCart;
import com.golovko.adminpanel.domain.OrderItem;
import com.golovko.adminpanel.domain.Product;
import com.golovko.adminpanel.dto.order.OrderPatchDTO;
import com.golovko.adminpanel.dto.order.OrderReadDTO;
import com.golovko.adminpanel.dto.order.OrderReadExtendedDTO;
import com.golovko.adminpanel.exception.EntityNotFoundException;
import com.golovko.adminpanel.repository.OrderRepository;
import com.golovko.adminpanel.repository.RepositoryHelper;
import com.golovko.adminpanel.service.OrderService;
import com.golovko.adminpanel.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RepositoryHelper repoHelper;

    @Autowired
    private TranslationService translationService;

    @Override
    @Transactional
    public OrderReadExtendedDTO getExtendedOrder(UUID customerId, UUID id) {
        OrderCart orderCart = getRequiredOrder(customerId, id);
        return translationService.translate(orderCart, OrderReadExtendedDTO.class);
    }

    @Override
    public List<OrderReadDTO> getAllCustomerOrders(UUID customerId) {
        List<OrderCart> orderCarts = orderRepository.findByCustomerId(customerId);
        return translationService.translateList(orderCarts, OrderReadDTO.class);
    }

    @Override
    @Transactional
    public OrderReadExtendedDTO patchOrder(UUID customerId, UUID id, OrderPatchDTO patchDTO) {
        OrderCart orderCart = getRequiredOrder(customerId, id);

        translationService.map(patchDTO, orderCart);
        orderCart = orderRepository.save(orderCart);

        return translationService.translate(orderCart, OrderReadExtendedDTO.class);
    }

    @Override
    public void deleteOrder(UUID customerId, UUID id) {
        OrderCart orderCart = getRequiredOrder(customerId, id);
        orderRepository.delete(orderCart);
    }

    //todo add filter
    @Override
    public List<OrderReadDTO> getAllOrders() {
        List<OrderCart> orderCarts = orderRepository.getAllOrders();
        return translationService.translateList(orderCarts, OrderReadDTO.class);
    }

    private OrderCart getRequiredOrder(UUID customerId, UUID id) {
        return Optional.ofNullable(orderRepository.findByIdAndCustomerId(id, customerId))
                .orElseThrow(() -> new EntityNotFoundException(OrderCart.class, id));
    }
}
