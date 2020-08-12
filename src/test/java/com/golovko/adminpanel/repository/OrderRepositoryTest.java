package com.golovko.adminpanel.repository;

import com.golovko.adminpanel.BaseTest;
import com.golovko.adminpanel.domain.Customer;
import com.golovko.adminpanel.domain.OrderCart;
import com.golovko.adminpanel.domain.OrderStatus;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderRepositoryTest extends BaseTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testFindByIdAndCustomerId() {
        Customer c1 = testObjectFactory.createCustomer();
        Customer c2 = testObjectFactory.createCustomer();

        OrderCart o1 = testObjectFactory.createOrder(c1);
        testObjectFactory.createOrder(c1);
        testObjectFactory.createOrder(c2);

        OrderCart orderCart = orderRepository.findByIdAndCustomerId(o1.getId(), c1.getId());

        Assert.assertEquals(o1.getId(), orderCart.getId());
    }

    @Test
    public void testFindByCustomerId() {
        Customer c1 = testObjectFactory.createCustomer();
        Customer c2 = testObjectFactory.createCustomer();

        OrderCart o1 = testObjectFactory.createOrder(c1);
        OrderCart o2 = testObjectFactory.createOrder(c1);
        testObjectFactory.createOrder(c2);

        List<OrderCart> orderCarts = orderRepository.findByCustomerId(c1.getId());

        Assertions.assertThat(orderCarts).extracting("id")
                .containsExactlyInAnyOrder(o1.getId(), o2.getId());
    }

    @Test
    public void testFindByStatus() {
        Customer c1 = testObjectFactory.createCustomer();
        Customer c2 = testObjectFactory.createCustomer();

        OrderCart o1 = testObjectFactory.createOrder(c1, OrderStatus.WAITING);
        testObjectFactory.createOrder(c1, OrderStatus.CANCELED);
        testObjectFactory.createOrder(c2, OrderStatus.COMPLETED);

        List<OrderCart> orderCarts = orderRepository.findByStatus(OrderStatus.WAITING);

        Assertions.assertThat(orderCarts).extracting("id")
                .containsExactlyInAnyOrder(o1.getId());
    }
}
