package com.golovko.adminpanel.util;

import com.golovko.adminpanel.domain.*;
import com.golovko.adminpanel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestObjectFactory {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public AppUser createUser() {
        AppUser user = new AppUser();
        user.setUsername("username");
        user.setChatId(333333L);
        user.setEncodedPassword("23432432");
        user.setIsBlocked(false);
        return appUserRepository.save(user);
    }

    public Category createCategory() {
        Category category = new Category();
        category.setName("shoes");
        return categoryRepository.save(category);
    }

    public Customer createCustomer() {
        Customer customer = new Customer();
        customer.setAddress("address");
        customer.setCity("Kiev");
        customer.setName("name");
        customer.setSurname("last name");
        customer.setUsername("username");
        customer.setChatId(123333L);
        customer.setPhone("38000788799");
        return customerRepository.save(customer);
    }

    public Product createProduct(Category category, Integer orderNumber) {
        Product p = new Product();
        p.setCategory(category);
        p.setOrderNumber(orderNumber);
        p.setPrice(2220.00);
        p.setPhotoUrl("url");
        p.setName("product name");
        p.setDescription("some description");
        return productRepository.save(p);
    }

    public OrderCart createOrder(Customer customer) {
        OrderCart orderCart = new OrderCart();
        orderCart.setCreatedDate(LocalDateTime.parse("2020-08-09T17:03:22.351"));
        orderCart.setTotalSum(20.0);
        orderCart.setStatus(OrderStatus.PROCESSED);
        orderCart.setCustomer(customer);
        return orderRepository.save(orderCart);
    }

    public OrderCart createOrder(Customer customer, OrderStatus status) {
        OrderCart orderCart = new OrderCart();
        orderCart.setCreatedDate(LocalDateTime.parse("2020-08-09T17:03:22.351"));
        orderCart.setTotalSum(20.0);
        orderCart.setStatus(status);
        orderCart.setCustomer(customer);
        return orderRepository.save(orderCart);
    }

    public OrderItem createOrderItem(OrderCart order, Product product, int quantity) {
        OrderItem item = new OrderItem(order, product, quantity);
        return orderItemRepository.save(item);
    }
}
