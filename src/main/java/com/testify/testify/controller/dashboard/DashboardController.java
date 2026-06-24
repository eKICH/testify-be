package com.testify.testify.controller.dashboard;

import com.testify.testify.dto.BugsBySeverityDto;
import com.testify.testify.dto.DashboardSummaryDto;
import com.testify.testify.dto.TestRunsByStatusDto;
import com.testify.testify.dto.UserStatisticsDto;
import com.testify.testify.service.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDto> getDashboardSummary() {
        DashboardSummaryDto summary = dashboardService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/test-runs-status")
    public ResponseEntity<TestRunsByStatusDto> getTestRunsByStatus() {
        TestRunsByStatusDto testRunsByStatus = dashboardService.getTestRunsByStatus();
        return ResponseEntity.ok(testRunsByStatus);
    }

    @GetMapping("/bugs-by-severity")
    public ResponseEntity<BugsBySeverityDto> getBugsBySeverity() {
        BugsBySeverityDto bugsBySeverity = dashboardService.getBugsBySeverity();
        return ResponseEntity.ok(bugsBySeverity);
    }

    @GetMapping("/user-statistics")
    public ResponseEntity<UserStatisticsDto> getUserStatistics() {
        UserStatisticsDto userStatistics = dashboardService.getUserStatistics();
        return ResponseEntity.ok(userStatistics);
    }
}
