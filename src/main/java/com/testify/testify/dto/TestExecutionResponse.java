package com.testify.testify.dto;

import com.testify.testify.entity.ExecutionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestExecutionResponse {
    private UUID id;
    private UUID testRunId;
    private UUID testCaseId;
    private ExecutionStatus status;
    private String actualResult;
    private String comments;
    private UserDto executedBy;
    private LocalDateTime executedAt;
}
