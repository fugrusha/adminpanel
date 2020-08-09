package com.golovko.adminpanel.service;

import com.golovko.adminpanel.BaseTest;
import com.golovko.adminpanel.domain.Customer;
import com.golovko.adminpanel.dto.customer.CustomerPatchDTO;
import com.golovko.adminpanel.dto.customer.CustomerReadDTO;
import com.golovko.adminpanel.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerServiceImplTest extends BaseTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testGetCustomer() {
        Customer c1 = testObjectFactory.createCustomer();

        CustomerReadDTO readDTO = customerService.getCustomer(c1.getId());

        Assertions.assertThat(c1).isEqualToIgnoringGivenFields(readDTO, "orders");
    }

    @Test
    public void testGetAllCustomers() {
        Customer c1 = testObjectFactory.createCustomer();
        Customer c2 = testObjectFactory.createCustomer();
        Customer c3 = testObjectFactory.createCustomer();

        List<CustomerReadDTO> allCustomers = customerService.getAllCustomers();

        Assertions.assertThat(allCustomers).extracting("id")
                .containsExactlyInAnyOrder(c1.getId(), c2.getId(), c3.getId());
    }

    @Test
    public void testPatchCustomer() {
        Customer c1 = testObjectFactory.createCustomer();

        CustomerPatchDTO patchDTO = new CustomerPatchDTO();
        patchDTO.setAddress("new address");
        patchDTO.setCity("new city");
        patchDTO.setName("new name");
        patchDTO.setSurname("new surname");
        patchDTO.setPhone("new Phone");
        patchDTO.setAddress("new address");

        CustomerReadDTO readDTO = customerService.patchCustomer(c1.getId(), patchDTO);

        Assertions.assertThat(patchDTO).isEqualToComparingFieldByField(readDTO);

        Customer customer = customerRepository.findById(c1.getId()).get();
        Assertions.assertThat(patchDTO).isEqualToComparingFieldByField(customer);

    }

    @Test
    public void testPatchCustomerWithEmptyPatch() {
        Customer c1 = testObjectFactory.createCustomer();

        CustomerPatchDTO patchDTO = new CustomerPatchDTO();

        CustomerReadDTO readDTO = customerService.patchCustomer(c1.getId(), patchDTO);
        Assertions.assertThat(readDTO).hasNoNullFieldsOrProperties();

        Customer customer = customerRepository.findById(c1.getId()).get();
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(customer);
    }
}
