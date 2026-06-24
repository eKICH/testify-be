package com.testify.testify.dto;

import com.testify.testify.entity.TestPlanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestPlanResponse {
    private Long id;
    private String name;
    private String description;
    private String scope;
    private LocalDate startDate;
    private LocalDate endDate;
    private TestPlanStatus status;
    private UserDto createdBy;
    private Integer totalTestCases;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
