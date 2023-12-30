package com.crm.customer.customerdatamanagement.exceptions;

import com.crm.customer.customerdatamanagement.constants.ErrorConstant;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
@Slf4j
public final class ApplicationExceptionHandler {

    /**
     * Method to handle constraint violation  exceptions.
     *
     * @param constraintViolationException the exception
     * @return the response entity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException constraintViolationException) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorConstant.BAD_REQUEST.getErrorCode(), getViolationMessageList(constraintViolationException));
        log.error("exception occurred while data creation - Error message: {}", constraintViolationException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Method to handle caught  customerNotFoundException exceptions.
     *
     * @param customerNotFoundException the exception
     * @return the response entity
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException customerNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(customerNotFoundException.getHttpStatusCode(), Collections.singletonList(customerNotFoundException.getMessage()));
        log.error("exception occurred while fetching customers - Error message: {}", customerNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Method to handle DataIntegrityViolationException exceptions.
     *
     * @param exception the exception parameter
     * @return the response entity
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleDataIntegrityException(DataIntegrityViolationException exception) {

        ErrorResponse errorResponse = new ErrorResponse(ErrorConstant.BAD_REQUEST_EMAIL_UNIQUE.getErrorCode(), Collections.singletonList(ErrorConstant.BAD_REQUEST_EMAIL_UNIQUE.getErrorMessage()));
        log.error("exception occurred while data creation - Error message: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Method to handle uncaught exceptions.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorConstant.INTERNAL_SERVER_ERROR.getErrorCode(), Collections.singletonList(exception.getMessage()));
        log.error("Uncaught exception found - HttpStatusCode: {}, Error message: {}", HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Method to handle invalid arguments on models.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        log.error("Exception occurred - HttpStatusCode: {}, Error message: {}", HttpStatus.BAD_REQUEST, exception.getMessage());
        var fieldError = exception.getBindingResult().getFieldError();
        if (fieldError != null && fieldError.getDefaultMessage() != null) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ErrorConstant.BAD_REQUEST.getErrorCode(), Collections.singletonList(fieldError.getDefaultMessage())));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorConstant.INVALID_REQUEST_BODY.getErrorMessage());
    }

    private List<String> getViolationMessageList(ConstraintViolationException violations) {
        return violations.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
    }
}