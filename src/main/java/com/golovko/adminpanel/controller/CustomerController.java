package com.golovko.adminpanel.controller;

import com.golovko.adminpanel.dto.CustomerPatchDTO;
import com.golovko.adminpanel.dto.CustomerReadDTO;
import com.golovko.adminpanel.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerReadDTO getCustomer(@PathVariable UUID id) {
        return customerService.getCustomer(id);
    }

    @GetMapping
    public List<CustomerReadDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PatchMapping("/{id}")
    public CustomerReadDTO patchCustomer(
            @PathVariable UUID id,
            @RequestBody CustomerPatchDTO patchDTO
    ) {
        return customerService.patchCustomer(id, patchDTO);
    }
}
