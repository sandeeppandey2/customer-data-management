package com.assignment.customerdatamanagement.utils;

import com.assignment.customerdatamanagement.models.Customer;
import com.assignment.customerdatamanagement.models.CustomerAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestUtil {
    public static Customer getCustomersRequest() {
        var address = CustomerAddress
                .builder()
                .country("Netherlands")
                .city("almere")
                .houseNumber("123A")
                .street("mystree")
                .postalCode("123la")
                .build();
        return Customer.
                builder()
                .customerFirstName("sandeep")
                .customerLastName("pandey")
                .age(18)
                .customerEmailAddress("sandeep1@gmail.com")
                .customerAddress(address)
                .build();
    }

    public static List<Customer> getCustomerResponseList() {
        List<Customer> customerList = new ArrayList<>();
        var address = CustomerAddress
                .builder()
                .country("Netherlands")
                .city("almere")
                .houseNumber("123A")
                .street("mystree")
                .postalCode("123la")
                .build();
        var customer1 = Customer.
                builder()
                .customerId(UUID.fromString("c8694a82-be08-4e2d-b002-bdbe34896a69"))
                .customerFirstName("sandeep")
                .customerLastName("pandey")
                .age(18)
                .customerEmailAddress("sandeep1@gmail.com")
                .customerAddress(address)
                .build();
        customerList.add(customer1);
        return customerList;
    }

    public static Customer getCustomersResponse() {
        var address = CustomerAddress
                .builder()
                .country("Netherlands")
                .city("almere")
                .houseNumber("123A")
                .street("mystree")
                .postalCode("123la")
                .build();
        return Customer.
                builder()
                .customerId(UUID.fromString("c8694a82-be08-4e2d-b002-bdbe34896a69"))
                .customerFirstName("sandeep")
                .customerLastName("pandey")
                .age(18)
                .customerEmailAddress("sandeep1@gmail.com")
                .customerAddress(address)
                .build();
    }

    public static Customer getIncorrectCustomerDetails() {
        var address = CustomerAddress
                .builder()
                .country("")
                .city("")
                .houseNumber("123A")
                .street("mystree")
                .postalCode("123la")
                .build();
        return Customer.
                builder()
                .customerFirstName("")
                .customerLastName("pandey")
                .age(18)
                .customerEmailAddress("sandeep1@gmail.com")
                .customerAddress(address)
                .build();
    }

    public static Customer getCustomerWithIncorrectEmailFormat() {
        var address = CustomerAddress
                .builder()
                .country("Netherlands")
                .city("almere")
                .houseNumber("123A")
                .street("mystree")
                .postalCode("123la")
                .build();
        return Customer.
                builder()
                .customerFirstName("sandeep")
                .customerLastName("pandey")
                .age(18)
                .customerEmailAddress("sandeep.com")
                .customerAddress(address)
                .build();
    }

    public static Customer customerUpdateRequest() {
        var address = CustomerAddress
                .builder()
                .country("Netherlands")
                .city("almere")
                .houseNumber("123A")
                .street("mystree")
                .postalCode("123la")
                .build();
        return Customer.
                builder()
                .customerId(UUID.fromString("c8694a82-be08-4e2d-b002-bdbe34896a69"))
                .customerFirstName("sandeep")
                .customerLastName("pandey")
                .age(18)
                .customerEmailAddress("sandeepupdate@gmail.com")
                .customerAddress(address)
                .build();
    }
}
