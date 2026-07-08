package com.testify.testify.dto;

import com.testify.testify.entity.Priority;
import com.testify.testify.entity.TestCaseStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TestCaseResponse {
    private UUID id;
    private String name;
    private String description;
    private String preconditions;
    private String steps;
    private String expectedResult;
    private Priority priority;
    private TestCaseStatus status;
    private UUID moduleId;
    private UUID createdById;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}