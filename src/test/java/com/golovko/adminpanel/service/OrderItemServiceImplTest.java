package com.golovko.adminpanel.service;

import com.golovko.adminpanel.BaseTest;
import com.golovko.adminpanel.domain.*;
import com.golovko.adminpanel.dto.order.OrderReadExtendedDTO;
import com.golovko.adminpanel.exception.EntityNotFoundException;
import com.golovko.adminpanel.repository.OrderItemRepository;
import com.golovko.adminpanel.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

public class OrderItemServiceImplTest extends BaseTest {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testAddProductToOrder() {
        Customer customer = testObjectFactory.createCustomer();
        OrderCart o1 = testObjectFactory.createOrder(customer);

        Category category = testObjectFactory.createCategory();
        Product p1 = testObjectFactory.createProduct(category, 1);

        OrderReadExtendedDTO extendedDTO = orderItemService.addProductToOrder(o1.getId(), p1.getId());

        Assert.assertEquals(1, extendedDTO.getItems().size());
        Assert.assertEquals(p1.getId(), extendedDTO.getItems().get(0).getProduct().getId());

        inTransaction(() -> {
            OrderCart order = orderRepository.findById(o1.getId()).get();
            Assert.assertEquals(1, order.getItems().size());
            Assert.assertEquals(p1.getId(), order.getItems().get(0).getProduct().getId());
        });
    }

    @Test(expected = EntityNotFoundException.class)
    public void testAddNotExistingProductToOrder() {
        Customer customer = testObjectFactory.createCustomer();
        OrderCart o1 = testObjectFactory.createOrder(customer);

        UUID productId = UUID.randomUUID();

        orderItemService.addProductToOrder(o1.getId(), productId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testAddNProductTootExistingOrder() {
        Category category = testObjectFactory.createCategory();
        Product p1 = testObjectFactory.createProduct(category, 1);

        UUID orderId = UUID.randomUUID();

        orderItemService.addProductToOrder(orderId, p1.getId());
    }

    @Test
    public void testRemoveProductFromOrder() {
        Customer customer = testObjectFactory.createCustomer();
        OrderCart o1 = testObjectFactory.createOrder(customer);

        Category category = testObjectFactory.createCategory();
        Product p1 = testObjectFactory.createProduct(category, 1);
        Product p2 = testObjectFactory.createProduct(category, 1);

        OrderItem item1 = testObjectFactory.createOrderItem(o1, p1, 4);
        OrderItem item2 = testObjectFactory.createOrderItem(o1, p2, 2);

        OrderReadExtendedDTO extendedDTO = orderItemService.removeProductFromOrder(o1.getId(), p1.getId());

        Assertions.assertThat(extendedDTO.getItems()).extracting("id").contains(item2.getId());
        Assertions.assertThat(extendedDTO.getItems()).extracting("id").doesNotContain(item1.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testRemoveNotExistingProductFromOrder() {
        Customer customer = testObjectFactory.createCustomer();
        OrderCart o1 = testObjectFactory.createOrder(customer);

        Category category = testObjectFactory.createCategory();
        Product p1 = testObjectFactory.createProduct(category, 1);

        OrderItem item = testObjectFactory.createOrderItem(o1, p1, 4);

        UUID wrongProductId = UUID.randomUUID();

        orderItemService.removeProductFromOrder(o1.getId(), wrongProductId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testRemoveProductFromNotExistingOrder() {
        Customer customer = testObjectFactory.createCustomer();
        OrderCart o1 = testObjectFactory.createOrder(customer);

        Category category = testObjectFactory.createCategory();
        Product p1 = testObjectFactory.createProduct(category, 1);

        OrderItem item = testObjectFactory.createOrderItem(o1, p1, 4);

        UUID wrongOrderId = UUID.randomUUID();

        orderItemService.removeProductFromOrder(wrongOrderId, p1.getId());
    }
}
