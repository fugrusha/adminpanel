package com.golovko.adminpanel.service.impl;

import com.golovko.adminpanel.domain.Customer;
import com.golovko.adminpanel.dto.customer.CustomerPatchDTO;
import com.golovko.adminpanel.dto.customer.CustomerReadDTO;
import com.golovko.adminpanel.repository.CustomerRepository;
import com.golovko.adminpanel.repository.RepositoryHelper;
import com.golovko.adminpanel.service.CustomerService;
import com.golovko.adminpanel.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RepositoryHelper repoHelper;

    @Override
    public CustomerReadDTO getCustomer(UUID id) {
        Customer customer = customerRepository.findById(id).get();
        return translationService.translate(customer, CustomerReadDTO.class);
    }

    @Override
    public List<CustomerReadDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.getAllCustomers();
        return translationService.translateList(customers, CustomerReadDTO.class);
    }

    @Override
    public CustomerReadDTO patchCustomer(UUID id, CustomerPatchDTO patchDTO) {
        Customer customer = repoHelper.getEntityById(Customer.class, id);

        translationService.map(patchDTO, customer);
        customer = customerRepository.save(customer);

        return translationService.translate(customer, CustomerReadDTO.class);
    }
}
