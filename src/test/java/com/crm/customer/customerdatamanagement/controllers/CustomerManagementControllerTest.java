package com.crm.customer.customerdatamanagement.controllers;

import com.crm.customer.customerdatamanagement.exceptions.ApplicationExceptionHandler;
import com.crm.customer.customerdatamanagement.models.Customer;
import com.crm.customer.customerdatamanagement.services.CustomerManagementService;
import com.crm.customer.customerdatamanagement.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Junit for controller")
class CustomerManagementControllerTest {
    @InjectMocks
    private CustomerManagementController classUnderTest;
    @Mock
    private CustomerManagementService customerManagementService;
    private MockMvc mockMvc;

    private static HttpHeaders getHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic Tm9uLVBlcnNvbmFsLUFjY291bnQ6S2E9MFo5TDRmODoz");
        return headers;
    }

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(classUnderTest)
                .setControllerAdvice(new ApplicationExceptionHandler())
                .build();
    }

    @SneakyThrows
    void verifyBodyRequestForCustomerCreation(Customer customer) {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer))
                )
                .andExpect(status().isBadRequest());
        Mockito.verify(customerManagementService, Mockito.times(0)).createCustomer(TestUtil.getCustomersRequest());
    }

    String asJsonString(final Customer obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    class HappyFlow {

        @Test
        @SneakyThrows
        @DisplayName("Should be able to create customer successfully")
        void testCreateCustomer() {
            when(customerManagementService.createCustomer(any())).thenReturn(TestUtil.getCustomersResponse());
            mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(TestUtil.getCustomersRequest()))
                            .headers(getHttpHeaders())
                    )
                    .andExpect(status().isOk());
            Mockito.verify(customerManagementService, Mockito.times(1)).createCustomer(TestUtil.getCustomersRequest());
        }

        @Test
        @SneakyThrows
        @DisplayName("should be able to fetch the customer successfully")
        void testFetchCustomer() {
            when(customerManagementService.fetchCustomer(any(), any())).thenReturn(TestUtil.getCustomerResponseList());
            mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(getHttpHeaders())
                    )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].customerFirstName", is("sandeep")))
                    .andExpect(jsonPath("$[0].customerLastName", is("pandey")))
                    .andExpect(jsonPath("$[0].customerEmailAddress", is("sandeep1@gmail.com")))
                    .andExpect(jsonPath("$[0].customerId", is("c8694a82-be08-4e2d-b002-bdbe34896a69")))
                    .andExpect(jsonPath("$[0].customerAddress").exists());
            Mockito.verify(customerManagementService, Mockito.times(1)).fetchCustomer(any(), any());
        }

        @Test
        @SneakyThrows
        @DisplayName("should be able to fetch the customer by Id successfully")
        void testFetchCustomerById() {
            when(customerManagementService.fetchCustomerById(any())).thenReturn(TestUtil.getCustomersResponse());
            mockMvc.perform(MockMvcRequestBuilders.get("/customers/c8694a82-be08-4e2d-b002-bdbe34896a69")
                            .contentType(MediaType.APPLICATION_JSON)
                            .headers(getHttpHeaders())
                    )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.customerFirstName", is("sandeep")))
                    .andExpect(jsonPath("$.customerLastName", is("pandey")))
                    .andExpect(jsonPath("$.customerEmailAddress", is("sandeep1@gmail.com")))
                    .andExpect(jsonPath("$.customerId", is("c8694a82-be08-4e2d-b002-bdbe34896a69")))
                    .andExpect(jsonPath("$.customerAddress").exists());
            Mockito.verify(customerManagementService, Mockito.times(1)).fetchCustomerById(any());
        }

        @Test
        @SneakyThrows
        @DisplayName("should be able to update specific  customer successfully")
        void testUpdateCustomer() {
            when(customerManagementService.updateCustomer(any(), any())).thenReturn(TestUtil.customerUpdateRequest());
            mockMvc.perform(MockMvcRequestBuilders.put("/customers/c8694a82-be08-4e2d-b002-bdbe34896a69")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(TestUtil.getCustomersRequest()))
                            .headers(getHttpHeaders())
                    )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.customerFirstName", is("sandeep")))
                    .andExpect(jsonPath("$.customerLastName", is("pandey")))
                    .andExpect(jsonPath("$.customerEmailAddress", is("sandeepupdate@gmail.com")))
                    .andExpect(jsonPath("$.customerId", is("c8694a82-be08-4e2d-b002-bdbe34896a69")))
                    .andExpect(jsonPath("$.customerAddress").exists());
            Mockito.verify(customerManagementService, Mockito.times(1)).updateCustomer(any(), any());
        }
    }

    @Nested
    class UnHappyFlow {
        @Test
        @SneakyThrows
        @DisplayName("should get bad request when trying to create customer with invalid email")
        void createCustomerWithEmptyEmail() {
            var customer = TestUtil.getCustomersRequest();
            customer.setCustomerEmailAddress("ss");
            verifyBodyRequestForCustomerCreation(customer);
        }

        @Test
        @SneakyThrows
        @DisplayName("should get bad request when trying to create customer with invalid first name")
        void createCustomerWithEmptyFirstName() {
            var customer = TestUtil.getCustomersRequest();
            customer.setCustomerFirstName("");
            verifyBodyRequestForCustomerCreation(customer);
        }

        @Test
        @SneakyThrows
        @DisplayName("should get bad request when trying to create customer with invalid last name")
        void createCustomerWithEmptyLastName() {
            var customer = TestUtil.getCustomersRequest();
            customer.setCustomerFirstName("");
            verifyBodyRequestForCustomerCreation(customer);
        }

        @Test
        @SneakyThrows
        @DisplayName("should get bad request when trying to create customer with invalid address")
        void createCustomerWithIncorrectAddress() {
            var customer = TestUtil.getCustomersRequest();
            customer.getCustomerAddress().setCity("");
            verifyBodyRequestForCustomerCreation(customer);
        }
    }
}

