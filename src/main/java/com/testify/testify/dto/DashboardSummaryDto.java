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

    // Explicit All-Arguments Constructor
    public DashboardSummaryDto(long totalTestCases, long totalTestPlans, long totalTestRuns, long totalBugs, long activeTestRuns, long openBugs) {
        this.totalTestCases = totalTestCases;
        this.totalTestPlans = totalTestPlans;
        this.totalTestRuns = totalTestRuns;
        this.totalBugs = totalBugs;
        this.activeTestRuns = activeTestRuns;
        this.openBugs = openBugs;
    }
}
