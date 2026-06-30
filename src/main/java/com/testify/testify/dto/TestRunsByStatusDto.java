package com.testify.testify.dto;

import com.testify.testify.entity.TestRunStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRunsByStatusDto {
    private Map<TestRunStatus, Long> testRunsByStatus;

    // Explicit All-Arguments Constructor
    public TestRunsByStatusDto(Map<TestRunStatus, Long> testRunsByStatus) {
        this.testRunsByStatus = testRunsByStatus;
    }
}
