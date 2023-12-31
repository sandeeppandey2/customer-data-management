package com.crm.customer.customerdatamanagement;

import com.crm.customer.customerdatamanagement.models.Customer;
import com.crm.customer.customerdatamanagement.repositories.CustomerRepository;
import com.crm.customer.customerdatamanagement.utils.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
@DisplayName("Integration test for customer data management")
class CustomerDataManagementIT {
    @Autowired
    private TestRestTemplate restTemplate;
    @Value("${basic.userName}")
    private String basicUserName;
    @Value("${basic.password}")
    private String basicPassword;
    @Autowired
    private CustomerRepository customerRepository;

    private static Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("customerFirstName", "sandeep");
        params.put("customerLastName", "pandey");
        return params;
    }

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    private void validateUpdatedCustomer(UUID customerId) {
        ResponseEntity<Customer> fetchUpdatedCustomer = restTemplate.withBasicAuth(basicUserName, basicPassword)
                .getForEntity("/customers/" + customerId, Customer.class);
        Assertions.assertEquals(fetchUpdatedCustomer.getStatusCode(), (HttpStatus.OK));
        Assertions.assertNotNull(fetchUpdatedCustomer.getBody());
        Assertions.assertEquals("Changed.email@email.com", fetchUpdatedCustomer.getBody().getCustomerEmailAddress());
    }

    private UUID updateCustomer(UUID createdCustomerId, ResponseEntity<Customer> fetchCustomerById) {
        var customer = fetchCustomerById.getBody();
        assert customer != null;
        customer.setCustomerEmailAddress("Changed.email@email.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Customer> httpEntity = new HttpEntity<>(customer, headers);
        ResponseEntity<Customer> updateCustomer = restTemplate.withBasicAuth(basicUserName, basicPassword)
                .exchange("/customers/" + createdCustomerId, HttpMethod.PUT, httpEntity, Customer.class);
        Assertions.assertEquals(updateCustomer.getStatusCode(), (HttpStatus.OK));
        Assertions.assertNotNull(updateCustomer.getBody());
        return updateCustomer.getBody().getCustomerId();
    }

    @Nested
    class HappyFlow {
        @Test
        @DisplayName("Create and fetch all customers by different filter and then update customer")
        void testCreateCustomersHappyFlow() {

            //Create new Customers
            ResponseEntity<Customer> createdResponse = restTemplate.withBasicAuth(basicUserName, basicPassword)
                    .postForEntity("/customers", TestUtil.getCustomersRequest(), Customer.class);
            Assertions.assertEquals(createdResponse.getStatusCode(), (HttpStatus.OK));
            Assertions.assertNotNull(createdResponse.getBody());
            var createdCustomerId = createdResponse.getBody().getCustomerId();

            //fetch all customers
            ResponseEntity<Customer[]> fetchedResponse = restTemplate.withBasicAuth(basicUserName, basicPassword)
                    .getForEntity("/customers", Customer[].class);
            validateListEntityResponse(fetchedResponse, createdCustomerId);

            //fetch customer by Id
            ResponseEntity<Customer> fetchCustomerById = restTemplate.withBasicAuth(basicUserName, basicPassword)
                    .getForEntity("/customers/" + createdCustomerId, Customer.class);
            validateSingleEntityResponse(fetchCustomerById, createdCustomerId);

            //fetch customer by query
            ResponseEntity<Customer[]> fetchCustomerByQueryParam = restTemplate.withBasicAuth(basicUserName, basicPassword)
                    .getForEntity("/customers?customerFirstName = {customerFirstName} & customerLastName = {customerLastName}", Customer[].class, getParams());
            validateListEntityResponse(fetchCustomerByQueryParam, createdCustomerId);

            //fetch customer by query by firstName
            ResponseEntity<Customer[]> fetchCustomerByQueryFirstName = restTemplate.withBasicAuth(basicUserName, basicPassword)
                    .getForEntity("/customers?customerFirstName = {customerFirstName}", Customer[].class, getParams().get("customerFirstName"));
            validateListEntityResponse(fetchCustomerByQueryFirstName, createdCustomerId);

            //update customer
            UUID customerId = updateCustomer(createdCustomerId, fetchCustomerById);

            //validate email is updated
            //fetch customer by Id
            validateUpdatedCustomer(customerId);
        }

        private void validateListEntityResponse(ResponseEntity<Customer[]> responseEntity, UUID createdCustomerId) {
            Assertions.assertEquals(responseEntity.getStatusCode(), (HttpStatus.OK));
            Assertions.assertNotNull(responseEntity.getBody());
            var customers = Arrays.asList(responseEntity.getBody());
            Assertions.assertEquals(createdCustomerId, customers.getFirst().getCustomerId());
            Assertions.assertEquals(TestUtil.getCustomersRequest().getAge(), customers.getFirst().getAge());
            Assertions.assertEquals(TestUtil.getCustomersRequest().getCustomerAddress(), customers.getFirst().getCustomerAddress());
            Assertions.assertEquals(TestUtil.getCustomersRequest().getCustomerEmailAddress(), customers.getFirst().getCustomerEmailAddress());
            Assertions.assertEquals(TestUtil.getCustomersRequest().getCustomerFirstName(), customers.getFirst().getCustomerFirstName());
            Assertions.assertEquals(TestUtil.getCustomersRequest().getCustomerLastName(), customers.getFirst().getCustomerLastName());
        }


        private void validateSingleEntityResponse(ResponseEntity<Customer> responseEntity, UUID createdCustomerId) {
            Assertions.assertEquals(responseEntity.getStatusCode(), (HttpStatus.OK));
            Assertions.assertNotNull(responseEntity.getBody());
            var customer = responseEntity.getBody();
            Assertions.assertEquals(createdCustomerId, customer.getCustomerId());
            Assertions.assertEquals(TestUtil.getCustomersRequest().getAge(), customer.getAge());
            Assertions.assertEquals(TestUtil.getCustomersRequest().getCustomerAddress(), customer.getCustomerAddress());
            Assertions.assertEquals(TestUtil.getCustomersRequest().getCustomerEmailAddress(), customer.getCustomerEmailAddress());
            Assertions.assertEquals(TestUtil.getCustomersRequest().getCustomerFirstName(), customer.getCustomerFirstName());
            Assertions.assertEquals(TestUtil.getCustomersRequest().getCustomerLastName(), customer.getCustomerLastName());
        }

    }

    @Nested
    class UnHappyFlow {
        @Test
        @DisplayName("Create customers with empty parameters")
        void createCustomerWithEmptyParameters() {

            ResponseEntity<String> createdResponse = restTemplate.withBasicAuth(basicUserName, basicPassword)
                    .postForEntity("/customers", TestUtil.getIncorrectCustomerDetails(), String.class);
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, createdResponse.getStatusCode());
            Assertions.assertNotNull(createdResponse.getBody());
            Assertions.assertTrue(createdResponse.getBody().contains("Customer First Name is mandatory"));
        }

        @Test
        @DisplayName("fetch non existed customers by id")
        void fetchNonExistedCustomer() {
            ResponseEntity<String> fetchCustomerById = restTemplate.withBasicAuth(basicUserName, basicPassword)
                    .getForEntity("/customers/" + UUID.randomUUID(), String.class);
            Assertions.assertEquals((HttpStatus.NOT_FOUND), fetchCustomerById.getStatusCode());
            Assertions.assertNotNull(fetchCustomerById.getBody());
            Assertions.assertTrue(fetchCustomerById.getBody().contains("Customer with given id does not exist"));
        }

        @Test
        @DisplayName("Create customers with incorrect format of email id")
        void createCustomerEmailWithIncorrectFormat() {
            ResponseEntity<String> createdResponse = restTemplate.withBasicAuth(basicUserName, basicPassword)
                    .postForEntity("/customers", TestUtil.getCustomerWithIncorrectEmailFormat(), String.class);
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, createdResponse.getStatusCode());
            Assertions.assertNotNull(createdResponse.getBody());
            Assertions.assertTrue(createdResponse.getBody().contains("Email is empty or email format is invalid"));
        }
    }
}