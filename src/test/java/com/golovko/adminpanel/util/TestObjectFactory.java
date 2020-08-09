package com.golovko.adminpanel.util;

import com.golovko.adminpanel.domain.AppUser;
import com.golovko.adminpanel.domain.Category;
import com.golovko.adminpanel.domain.Customer;
import com.golovko.adminpanel.domain.Product;
import com.golovko.adminpanel.repository.AppUserRepository;
import com.golovko.adminpanel.repository.CategoryRepository;
import com.golovko.adminpanel.repository.CustomerRepository;
import com.golovko.adminpanel.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public AppUser createUser() {
        AppUser user = new AppUser();
        user.setUsername("username");
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
        customer.setChatId("1234");
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
}
