package com.golovko.adminpanel.repository;

import com.golovko.adminpanel.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {
}
