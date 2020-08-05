package com.golovko.adminpanel.service.impl;

import com.golovko.adminpanel.domain.Customer;
import com.golovko.adminpanel.dto.CustomerPatchDTO;
import com.golovko.adminpanel.dto.CustomerReadDTO;
import com.golovko.adminpanel.repository.CustomerRepository;
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

    @Override
    public CustomerReadDTO getCustomer(UUID id) {
        Customer customer = customerRepository.findById(id).get();

        return translationService.translate(customer, CustomerReadDTO.class);
    }

    @Override
    public List<CustomerReadDTO> getAllCustomers() {
        return null;
    }

    @Override
    public CustomerReadDTO patchCustomer(UUID id, CustomerPatchDTO patchDTO) {
        return null;
    }
}
