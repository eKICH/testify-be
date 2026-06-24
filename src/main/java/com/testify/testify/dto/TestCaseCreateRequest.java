package com.testify.testify.dto;

import com.testify.testify.entity.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseCreateRequest {
    @NotBlank
    private String name;

    private String description;
    private String preconditions;
    private String steps;
    private String expectedResult;

    @NotNull
    private Priority priority;

    private Long testSuiteId;
}
