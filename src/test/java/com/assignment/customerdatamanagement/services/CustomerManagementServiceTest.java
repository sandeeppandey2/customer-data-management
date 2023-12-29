package com.assignment.customerdatamanagement.services;

import com.assignment.customerdatamanagement.exceptions.CustomerNotFoundException;
import com.assignment.customerdatamanagement.models.Customer;
import com.assignment.customerdatamanagement.repositories.CustomerRepository;
import com.assignment.customerdatamanagement.utils.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Junit for service class")
class CustomerManagementServiceTest {

    @InjectMocks
    private CustomerManagementService classUnderTest;

    @Mock
    private CustomerRepository customerRepository;

    void validate(List<Customer> customer) {
        assertNotNull(customer);
        assertNotNull(customer.getFirst().getCustomerId());
        assertEquals(TestUtil.getCustomersRequest().getCustomerFirstName(), customer.getFirst().getCustomerFirstName());
        assertEquals(TestUtil.getCustomersRequest().getCustomerLastName(), customer.getFirst().getCustomerLastName());
        assertEquals(TestUtil.getCustomersRequest().getCustomerEmailAddress(), customer.getFirst().getCustomerEmailAddress());
        assertEquals(TestUtil.getCustomersRequest().getAge(), customer.getFirst().getAge());
        assertNotNull(customer.getFirst().getCustomerAddress());
    }

    @Nested
    class HappyFlow {
        @Test
        @DisplayName("Create valid customer")
        void testCreateCustomer() {
            when(customerRepository.save(any())).thenReturn(TestUtil.getCustomersResponse());
            var customer = classUnderTest.createCustomer(TestUtil.getCustomersRequest());
            validate(Collections.singletonList(customer));
        }

        @Test
        @DisplayName("fetch valid customer by id")
        void testFetchCustomerById() {
            when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(TestUtil.getCustomersResponse()));
            var customer = classUnderTest.fetchCustomerById(UUID.fromString("c8694a82-be08-4e2d-b002-bdbe34896a69"));
            validate(Collections.singletonList(customer));
        }

        @Test
        @DisplayName("fetch all customer")
        void testFetchAllCustomer() {
            when(customerRepository.findAll()).thenReturn(TestUtil.getCustomerResponseList());
            var customer = classUnderTest.fetchCustomer(Optional.empty(), Optional.empty());
            validate(customer);
        }


        @Test
        @DisplayName("fetch  customer by query firstname")
        void testFetchByCustomerFirstName() {
            when(customerRepository.findByCustomerFirstNameIgnoreCase(any())).thenReturn(TestUtil.getCustomerResponseList());
            var customer = classUnderTest.fetchCustomer(Optional.of("sandeep"), Optional.empty());
            validate(customer);
        }

        @Test
        @DisplayName("fetch  customer by query last name")
        void testFetchByCustomerLastName() {
            when(customerRepository.findByCustomerLastNameIgnoreCase(any())).thenReturn(TestUtil.getCustomerResponseList());
            var customer = classUnderTest.fetchCustomer(Optional.empty(), Optional.of("pandey"));
            validate(customer);
        }

        @Test
        @DisplayName("fetch  customer by query both first and last name")
        void testFetchByCustomerFirstAndLastName() {
            when(customerRepository.findByCustomerFirstNameIgnoreCaseAndCustomerLastNameIgnoreCase(any(), any())).thenReturn(TestUtil.getCustomerResponseList());
            var customer = classUnderTest.fetchCustomer(Optional.of("sandeep"), Optional.of("pandey"));
            validate(customer);
        }
    }

    @Nested
    class UnHappyFlow {

        @Test
        @DisplayName("should throw exception when no customer found by given id ")
        void testFetchCustomerByInvalidId() {
            UUID uuid = UUID.randomUUID();
            assertThrows(CustomerNotFoundException.class, () -> classUnderTest.fetchCustomerById(uuid));
        }

        @Test
        @DisplayName("should throw exception when no customer found by given fast name ")
        void testFetchCustomerByNonExistedFirstName() {
            //Sonar rule fix
            Optional<String> firstName = Optional.of("Arya");
            Optional<String> lastName = Optional.empty();
            assertThrows(CustomerNotFoundException.class, () -> classUnderTest.fetchCustomer(firstName, lastName));
        }

        @Test
        @DisplayName("should throw exception when no customer found by given last name ")
        void testFetchCustomerByNonExistedLastName() {
            //Sonar rule fix
            Optional<String> firstName = Optional.empty();
            Optional<String> lastName = Optional.of("ryan");
            assertThrows(CustomerNotFoundException.class, () -> classUnderTest.fetchCustomer(firstName, lastName));
        }

        @Test
        @DisplayName("should throw exception while updating non existed customer id ")
        void testUpdateCustomerByNonExistedId() {
            //Sonar rule fix
            UUID uuid = UUID.randomUUID();
            var customerDto = TestUtil.customerUpdateRequest();
            assertThrows(CustomerNotFoundException.class, () -> classUnderTest.updateCustomer(customerDto, uuid));
        }
    }

}

