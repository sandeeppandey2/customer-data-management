package com.assignment.customerdatamanagement.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit test for handling Exceptions")
class ApplicationExceptionHandlerTest {
    @InjectMocks
    private ApplicationExceptionHandler classUnderTest;

    @Test
    @DisplayName("test constraint violation exception ")
    void testHandleConstraintViolation() {
        var responseEntity = classUnderTest.handleConstraintViolation(new ConstraintViolationException(
                new HashSet<>(Integer.parseInt(String.valueOf(2)))));
        Assertions.assertNotNull(responseEntity);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
    }

    @Test
    @DisplayName("test data integrity violation exception ")
    void testHandleDataIntegrityViolation() {
        var responseEntity = classUnderTest.handleDataIntegrityException(new DataIntegrityViolationException("Exception while creating customer"));
        Assertions.assertNotNull(responseEntity);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
    }

    @Test
    @DisplayName("test generic exception ")
    void testHandleGenericException() {
        var responseEntity = classUnderTest.handleGenericException(new Exception("generic error"));
        Assertions.assertNotNull(responseEntity);
        Assertions.assertTrue(responseEntity.getStatusCode().is5xxServerError());
    }
}

