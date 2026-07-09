package com.testify.testify.dto;

import com.testify.testify.entity.TestRunStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRunResponse {
    private Long id;
    private String name;
    private String description;
    private String buildVersion;
    private String environment;
    private TestRunStatus status;
    private LocalDateTime executionStartDate;
    private LocalDateTime executionEndDate;
    private TestRunMetricsDto metrics;
    private UserDto createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
