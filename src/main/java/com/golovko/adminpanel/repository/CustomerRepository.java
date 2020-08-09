package com.golovko.adminpanel.repository;

import com.golovko.adminpanel.domain.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {

    @Query("from Customer")
    List<Customer> getAllCustomers();
}
