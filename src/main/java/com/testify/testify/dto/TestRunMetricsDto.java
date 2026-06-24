package com.testify.testify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRunMetricsDto {
    private Integer totalTestCount;
    private Integer passedCount;
    private Integer failedCount;
    private Integer blockedCount;
    private Double passPercentage;
}
