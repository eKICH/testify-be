package com.testify.testify.dto;

import com.testify.testify.entity.ExecutionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestExecutionRequest {
    @NotNull
    private UUID testCaseId;

    @NotNull
    private ExecutionStatus status;

    private String actualResult;
    private String comments;

    // Explicit Getters
    public UUID getTestCaseId() { return testCaseId; }
    public ExecutionStatus getStatus() { return status; }
    public String getActualResult() { return actualResult; }
    public String getComments() { return comments; }
}
