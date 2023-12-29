package com.assignment.customerdatamanagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class CustomerDataManagementApplicationTests {

    @Test
    void contextLoads(ApplicationContext applicationContext) {
        Assertions.assertNotNull(applicationContext);
    }

}