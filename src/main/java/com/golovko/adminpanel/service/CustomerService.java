package com.golovko.adminpanel.service;

import com.golovko.adminpanel.dto.customer.CustomerPatchDTO;
import com.golovko.adminpanel.dto.customer.CustomerReadDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerReadDTO getCustomer(UUID id);

    List<CustomerReadDTO> getAllCustomers();

    CustomerReadDTO patchCustomer(UUID id, CustomerPatchDTO patchDTO);
}
