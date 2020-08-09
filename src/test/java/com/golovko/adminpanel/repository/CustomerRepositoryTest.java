package com.golovko.adminpanel.repository;

import com.golovko.adminpanel.BaseTest;
import com.golovko.adminpanel.domain.Customer;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerRepositoryTest extends BaseTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testGetAllCustomers() {
        Customer c1 = testObjectFactory.createCustomer();
        Customer c2 = testObjectFactory.createCustomer();
        Customer c3 = testObjectFactory.createCustomer();

        List<Customer> allCustomers = customerRepository.getAllCustomers();

        Assertions.assertThat(allCustomers).extracting("id")
                .containsExactlyInAnyOrder(c1.getId(), c2.getId(), c3.getId());
    }
}
