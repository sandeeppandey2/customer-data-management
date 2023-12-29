package com.assignment.customerdatamanagement.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private String errorCode;
    private List<String> errorMessages;
}