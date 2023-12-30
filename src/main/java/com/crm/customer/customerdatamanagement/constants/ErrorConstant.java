package com.crm.customer.customerdatamanagement.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorConstant {
    CUSTOMER_NOT_FOUND("CUSTOMER_NOT_FOUND", "Customer with given id does not exist"),
    CUSTOMERS_NOT_EXIST("CUSTOMER_NOT_EXIST", "Customers do not exist"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Something went wrong. Please try again later"),
    BAD_REQUEST("BAD_REQUEST", "Please enter all required fields"),
    BAD_REQUEST_EMAIL_UNIQUE("BAD_REQUEST_EMAIL_UNIQUE", "Email address must be unique"),
    INVALID_REQUEST_BODY("BAD_REQUEST_EMAIL_UNIQUE", "Invalid input supplied in request body.");

    private final String errorCode;
    private final String errorMessage;
}

