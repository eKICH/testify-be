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

    // New home for test cases (replaces testSuiteId - see migration checklist).
    private Long moduleId;

    // Explicit Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPreconditions() { return preconditions; }
    public String getSteps() { return steps; }
    public String getExpectedResult() { return expectedResult; }
    public Priority getPriority() { return priority; }
    public Long getTestSuiteId() { return testSuiteId; }
    public Long getModuleId() { return moduleId; }
}
