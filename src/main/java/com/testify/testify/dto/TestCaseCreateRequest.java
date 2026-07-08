package com.testify.testify.dto;

import com.testify.testify.entity.Priority;
import com.testify.testify.entity.TestCaseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseCreateRequest {

    @NotNull(message = "Module ID cannot be null")
    private UUID moduleId;

    @NotBlank(message = "Test case name cannot be blank")
    private String name;

    private String description;
    private String preconditions;
    private String steps;
    private String expectedResult;

    @NotNull(message = "Priority cannot be null")
    private Priority priority;

    // The status can be optional on creation, defaulting in the service if needed.
    private TestCaseStatus status;
}
