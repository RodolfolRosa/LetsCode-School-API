package com.study.quarkus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class ErrorResponse {

    private String message;
    private Collection<ErrorMessage> errors;

    public static ErrorResponse createFromValidation(ConstraintViolationException constraintViolationException) {
        var violations =
                constraintViolationException
                .getConstraintViolations()
                .stream()
                .map(cv -> new ErrorMessage(cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());
        return new ErrorResponse("Validation Errors", violations);

    }
}
