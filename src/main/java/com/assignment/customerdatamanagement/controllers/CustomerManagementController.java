package com.assignment.customerdatamanagement.controllers;


import com.assignment.customerdatamanagement.models.Customer;
import com.assignment.customerdatamanagement.services.CustomerManagementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Validated
@AllArgsConstructor
@Slf4j
@RequestMapping("/customers")
@SecurityRequirement(name = "Basic Auth")
public class CustomerManagementController {

    private final CustomerManagementService customerManagementService;

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> fetchCustomerById(final @PathVariable UUID customerId) {
        return ResponseEntity.ok(customerManagementService.fetchCustomerById(customerId));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> fetchCustomers(final @RequestParam(name = "customerFirstName", required = false) Optional<String> firstName,
                                                         final @RequestParam(name = "customerLastName", required = false) Optional<String> lastName) {
        return ResponseEntity.ok(customerManagementService.fetchCustomer(firstName, lastName));

    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(final @Valid @RequestBody Customer customer) {
        return ResponseEntity.ok(customerManagementService.createCustomer(customer));

    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomers(final @PathVariable UUID customerId, final @RequestBody Customer customer) {
        return ResponseEntity.ok(customerManagementService.updateCustomer(customer, customerId));
    }
}

