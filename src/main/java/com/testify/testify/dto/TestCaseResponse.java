package com.testify.testify.dto;

import com.testify.testify.entity.Priority;
import com.testify.testify.entity.TestCaseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseResponse {
    private UUID id;
    private String name;
    private String description;
    private String preconditions;
    private String steps;
    private String expectedResult;
    private Priority priority;
    private TestCaseStatus status;
    private UserDto createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
