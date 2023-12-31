package com.crm.customer.customerdatamanagement.controllers;


import com.crm.customer.customerdatamanagement.models.Customer;
import com.crm.customer.customerdatamanagement.services.CustomerManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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


@Tag(name = "Customer management", description = "Apis to manage customer data")
@RequestMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "Basic Auth")
@Slf4j
@Validated
@AllArgsConstructor
@RestController
public class CustomerManagementController {

    private final CustomerManagementService customerManagementService;

    @Operation(summary = "fetch customer by id")
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> fetchCustomerById(final @PathVariable UUID customerId) {
        return ResponseEntity.ok(customerManagementService.fetchCustomerById(customerId));
    }

    @Operation(summary = "fetch all customers")
    @GetMapping

    public ResponseEntity<List<Customer>> fetchCustomers(final @RequestParam(name = "customerFirstName", required = false) Optional<String> firstName,
                                                         final @RequestParam(name = "customerLastName", required = false) Optional<String> lastName) {
        return ResponseEntity.ok(customerManagementService.fetchCustomer(firstName, lastName));

    }

    @Operation(summary = "add customer to database")
    @PostMapping
    public ResponseEntity<Customer> createCustomer(final @Valid @RequestBody Customer customer) {
        return ResponseEntity.ok(customerManagementService.createCustomer(customer));

    }

    @Operation(summary = "update specific customer in database")
    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomers(final @PathVariable UUID customerId, final @RequestBody Customer customer) {
        return ResponseEntity.ok(customerManagementService.updateCustomer(customer, customerId));
    }
}