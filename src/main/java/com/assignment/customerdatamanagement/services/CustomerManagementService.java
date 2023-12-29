package com.assignment.customerdatamanagement.services;

import com.assignment.customerdatamanagement.constants.ErrorConstant;
import com.assignment.customerdatamanagement.exceptions.CustomerNotFoundException;
import com.assignment.customerdatamanagement.models.Customer;
import com.assignment.customerdatamanagement.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class CustomerManagementService {

    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);

    }

    public Customer fetchCustomerById(UUID customerId) {

        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(ErrorConstant.CUSTOMER_NOT_FOUND.getErrorCode(),
                        ErrorConstant.CUSTOMER_NOT_FOUND.getErrorMessage()));
    }

    public Customer updateCustomer(Customer customer, UUID customerId) {
        Customer savedCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(ErrorConstant.CUSTOMER_NOT_FOUND.getErrorCode(),
                        ErrorConstant.CUSTOMER_NOT_FOUND.getErrorMessage()));
        savedCustomer.setCustomerEmailAddress(customer.getCustomerEmailAddress());
        savedCustomer.setCustomerAddress(customer.getCustomerAddress());
        return customerRepository.save(savedCustomer);
    }

    public List<Customer> fetchCustomer(Optional<String> firstName, Optional<String> lastName) {

        if (firstName.isPresent() && lastName.isPresent()) {
            var customerList = customerRepository.findByCustomerFirstNameIgnoreCaseAndCustomerLastNameIgnoreCase(firstName, lastName);
            return fetchCustomer(customerList);

        } else if (firstName.isPresent()) {
            var customerList = customerRepository.findByCustomerFirstNameIgnoreCase(firstName);
            return fetchCustomer(customerList);

        } else if (lastName.isPresent()) {
            var customerList = customerRepository.findByCustomerLastNameIgnoreCase(firstName);
            return fetchCustomer(customerList);
        }
        return customerRepository.findAll();
    }

    private List<Customer> fetchCustomer(List<Customer> customerList) {
        if (customerList.isEmpty()) {
            throw new CustomerNotFoundException(ErrorConstant.CUSTOMER_NOT_FOUND.getErrorCode(),
                    ErrorConstant.CUSTOMER_NOT_FOUND.getErrorMessage());
        }
        return customerList;
    }
}

