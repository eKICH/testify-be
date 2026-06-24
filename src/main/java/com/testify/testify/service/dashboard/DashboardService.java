package com.testify.testify.service.dashboard;

import com.testify.testify.dto.BugsBySeverityDto;
import com.testify.testify.dto.DashboardSummaryDto;
import com.testify.testify.dto.TestRunsByStatusDto;
import com.testify.testify.dto.UserStatisticsDto;

public interface DashboardService {
    DashboardSummaryDto getDashboardSummary();
    TestRunsByStatusDto getTestRunsByStatus();
    BugsBySeverityDto getBugsBySeverity();
    UserStatisticsDto getUserStatistics();
}
