package com.testify.testify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDto {
    private long totalTestCases;
    private long totalTestPlans;
    private long totalTestRuns;
    private long totalBugs;
    private long activeTestRuns;
    private long openBugs;
}
