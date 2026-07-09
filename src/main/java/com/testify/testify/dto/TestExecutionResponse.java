package com.testify.testify.dto;

import com.testify.testify.entity.ExecutionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestExecutionResponse {
    private Long id;
    private Long testRunId;
    private Long testCaseId;
    private ExecutionStatus status;
    private String actualResult;
    private String comments;
    private UserDto executedBy;
    private LocalDateTime executedAt;
}
