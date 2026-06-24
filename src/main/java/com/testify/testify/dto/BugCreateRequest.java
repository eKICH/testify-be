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
}
