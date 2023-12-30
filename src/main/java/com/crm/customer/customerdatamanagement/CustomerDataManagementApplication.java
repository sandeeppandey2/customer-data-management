package com.crm.customer.customerdatamanagement;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication()
@SecurityScheme(name = "Basic Auth", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class CustomerDataManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerDataManagementApplication.class, args);
    }
}

