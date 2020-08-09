package com.golovko.adminpanel.service.impl;

import com.golovko.adminpanel.domain.OrderCart;
import com.golovko.adminpanel.domain.OrderItem;
import com.golovko.adminpanel.domain.Product;
import com.golovko.adminpanel.dto.order.OrderReadExtendedDTO;
import com.golovko.adminpanel.exception.EntityNotFoundException;
import com.golovko.adminpanel.repository.OrderItemRepository;
import com.golovko.adminpanel.repository.OrderRepository;
import com.golovko.adminpanel.repository.RepositoryHelper;
import com.golovko.adminpanel.service.OrderItemService;
import com.golovko.adminpanel.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private RepositoryHelper repoHelper;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderReadExtendedDTO addProductToOrder(UUID orderId, UUID productId) {
        OrderCart order = repoHelper.getEntityById(OrderCart.class, orderId);
        Product product = repoHelper.getReferenceIfExist(Product.class, productId);

        OrderItem item = new OrderItem(order, product, 1);
        item = orderItemRepository.save(item);

        order.getItems().add(item);
        order = orderRepository.save(order);

        return translationService.translate(order, OrderReadExtendedDTO.class);
    }

    @Override
    @Transactional
    public OrderReadExtendedDTO removeProductFromOrder(UUID orderId, UUID productId) {
        OrderCart order = repoHelper.getEntityById(OrderCart.class, orderId);

        boolean removed = order.getItems().removeIf(item -> item.getProduct().getId().equals(productId));

        if (!removed) {
            throw new EntityNotFoundException("Order " + orderId + " has no such product with id: " + productId);
        }

        order = orderRepository.save(order);

        return translationService.translate(order, OrderReadExtendedDTO.class);
    }
}
