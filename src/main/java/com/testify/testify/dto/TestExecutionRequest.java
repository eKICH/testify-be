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
}
