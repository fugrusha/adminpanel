package com.golovko.adminpanel.service;

import com.golovko.adminpanel.BaseTest;
import com.golovko.adminpanel.domain.*;
import com.golovko.adminpanel.dto.order.OrderFilter;
import com.golovko.adminpanel.dto.order.OrderPatchDTO;
import com.golovko.adminpanel.dto.order.OrderReadDTO;
import com.golovko.adminpanel.dto.order.OrderReadExtendedDTO;
import com.golovko.adminpanel.exception.EntityNotFoundException;
import com.golovko.adminpanel.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class OrderServiceImplTest extends BaseTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testGetExtendedOrder() {
        Customer customer = testObjectFactory.createCustomer();
        Category category = testObjectFactory.createCategory();
        Product p1 = testObjectFactory.createProduct(category, 1);
        Product p2 = testObjectFactory.createProduct(category, 2);

        OrderCart order = testObjectFactory.createOrder(customer);
        OrderItem item1 = testObjectFactory.createOrderItem(order, p1, 2);
        OrderItem item2 = testObjectFactory.createOrderItem(order, p1, 2);

        OrderReadExtendedDTO extendedReadDTO = orderService.getExtendedOrder(customer.getId(), order.getId());

        Assertions.assertThat(order).isEqualToIgnoringGivenFields(extendedReadDTO,
                "items", "customer");
        Assert.assertEquals(customer.getId(), extendedReadDTO.getCustomer().getId());
        Assert.assertEquals(2, extendedReadDTO.getItems().size());
        Assertions.assertThat(extendedReadDTO.getItems()).extracting("id")
                .containsExactlyInAnyOrder(item1.getId(), item2.getId());
    }

    @Test
    public void testGetAllCustomerOrders() {
        Customer c1 = testObjectFactory.createCustomer();
        Customer c2 = testObjectFactory.createCustomer();

        OrderCart o1 = testObjectFactory.createOrder(c1);
        OrderCart o2 = testObjectFactory.createOrder(c1);
        testObjectFactory.createOrder(c2);

        List<OrderReadDTO> allCustomerOrders = orderService.getAllCustomerOrders(c1.getId());

        Assertions.assertThat(allCustomerOrders).extracting("id")
                .containsExactlyInAnyOrder(o1.getId(), o2.getId());
    }

    @Test
    public void testPatchOrder() {
        Customer c1 = testObjectFactory.createCustomer();
        OrderCart o1 = testObjectFactory.createOrder(c1);

        OrderPatchDTO patchDTO = new OrderPatchDTO();
        patchDTO.setStatus(OrderStatus.COMPLETED);

        OrderReadExtendedDTO extendedDTO = orderService.patchOrder(c1.getId(), o1.getId(), patchDTO);
        Assert.assertEquals(OrderStatus.COMPLETED, extendedDTO.getStatus());

        OrderCart order = orderRepository.findById(o1.getId()).get();
        Assert.assertEquals(OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    public void testPatchOrderEmptyPatch() {
        Customer c1 = testObjectFactory.createCustomer();
        OrderCart o1 = testObjectFactory.createOrder(c1);

        OrderPatchDTO patchDTO = new OrderPatchDTO();

        OrderReadExtendedDTO extendedDTO = orderService.patchOrder(c1.getId(), o1.getId(), patchDTO);
        Assert.assertEquals(o1.getStatus(), extendedDTO.getStatus());

        OrderCart order = orderRepository.findById(o1.getId()).get();
        Assert.assertEquals(o1.getStatus(), order.getStatus());
    }

    @Test
    public void testDeleteOrder() {
        Customer c1 = testObjectFactory.createCustomer();
        OrderCart o1 = testObjectFactory.createOrder(c1);

        orderService.deleteOrder(c1.getId(), o1.getId());

        Assert.assertFalse(orderRepository.existsById(o1.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNotExistingOrder() {
        Customer c1 = testObjectFactory.createCustomer();

        orderService.deleteOrder(c1.getId(), UUID.randomUUID());
    }

    @Test
    public void testGetAllOrdersWithEmptyFilter() {
        Customer c1 = testObjectFactory.createCustomer();
        Customer c2 = testObjectFactory.createCustomer();

        OrderCart o1 = testObjectFactory.createOrder(c1);
        OrderCart o2 = testObjectFactory.createOrder(c1);
        OrderCart o3 = testObjectFactory.createOrder(c2);

        List<OrderReadDTO> allOrders = orderService.getAllOrders(new OrderFilter());

        Assertions.assertThat(allOrders).extracting("id")
                .containsExactlyInAnyOrder(o1.getId(), o2.getId(), o3.getId());
    }

    @Test
    public void testGetAllOrdersWithFilter() {
        Customer c1 = testObjectFactory.createCustomer();
        Customer c2 = testObjectFactory.createCustomer();

        OrderCart o1 = testObjectFactory.createOrder(c1, OrderStatus.COMPLETED);
        testObjectFactory.createOrder(c2, OrderStatus.WAITING);
        testObjectFactory.createOrder(c1, OrderStatus.CANCELED);

        OrderFilter filter = new OrderFilter();
        filter.setStatus(OrderStatus.COMPLETED);

        List<OrderReadDTO> allOrders = orderService.getAllOrders(filter);

        Assertions.assertThat(allOrders).extracting("id").contains(o1.getId());
    }
}
