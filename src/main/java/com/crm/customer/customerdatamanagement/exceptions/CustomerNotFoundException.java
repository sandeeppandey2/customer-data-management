package com.crm.customer.customerdatamanagement.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CustomerNotFoundException extends RuntimeException {

    private final String httpStatusCode;
    private final String message;


}
