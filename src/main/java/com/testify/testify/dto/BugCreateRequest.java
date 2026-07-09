package com.testify.testify.dto;

import com.testify.testify.entity.Priority;
import com.testify.testify.entity.Severity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugCreateRequest {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Severity severity;

    @NotNull
    private Priority priority;

    private String stepsToReproduce;
    private String expectedBehavior;
    private String actualBehavior;
    private String environment;

    private Long testCaseId;
    private Long testRunId;

    // Explicit Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Severity getSeverity() { return severity; }
    public Priority getPriority() { return priority; }
    public String getStepsToReproduce() { return stepsToReproduce; }
    public String getExpectedBehavior() { return expectedBehavior; }
    public String getActualBehavior() { return actualBehavior; }
    public String getEnvironment() { return environment; }
    public Long getTestCaseId() { return testCaseId; }
    public Long getTestRunId() { return testRunId; }
}
