package com.testify.testify.dto;

import com.testify.testify.entity.ExecutionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestExecutionRequest {
    @NotNull
    private Long testCaseId;

    @NotNull
    private ExecutionStatus status;

    private String actualResult;
    private String comments;

    // Explicit Getters
    public Long getTestCaseId() { return testCaseId; }
    public ExecutionStatus getStatus() { return status; }
    public String getActualResult() { return actualResult; }
    public String getComments() { return comments; }
}
