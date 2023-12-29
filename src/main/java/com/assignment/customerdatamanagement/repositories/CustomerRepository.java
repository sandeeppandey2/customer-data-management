package com.assignment.customerdatamanagement.repositories;

import com.assignment.customerdatamanagement.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer> findByCustomerFirstNameIgnoreCaseAndCustomerLastNameIgnoreCase(Optional<String> customerFirstName, Optional<String> customerLastName);

    List<Customer> findByCustomerFirstNameIgnoreCase(Optional<String> customerFirstName);

    List<Customer> findByCustomerLastNameIgnoreCase(Optional<String> customerLastName);

}




