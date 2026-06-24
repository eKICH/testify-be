package com.testify.testify.service.dashboard;

import com.testify.testify.dto.BugsBySeverityDto;
import com.testify.testify.dto.DashboardSummaryDto;
import com.testify.testify.dto.TestRunsByStatusDto;
import com.testify.testify.dto.UserStatisticsDto;
import com.testify.testify.entity.BugStatus;
import com.testify.testify.entity.Severity;
import com.testify.testify.entity.TestRunStatus;
import com.testify.testify.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final TestCaseRepository testCaseRepository;
    private final TestPlanRepository testPlanRepository;
    private final TestRunRepository testRunRepository;
    private final BugRepository bugRepository;
    private final UserRepository userRepository;

    @Override
    public DashboardSummaryDto getDashboardSummary() {
        long totalTestCases = testCaseRepository.count();
        long totalTestPlans = testPlanRepository.count();
        long totalTestRuns = testRunRepository.count();
        long totalBugs = bugRepository.count();
        long activeTestRuns = testRunRepository.countByStatus(TestRunStatus.IN_PROGRESS);
        long openBugs = bugRepository.countByStatus(BugStatus.OPEN);

        return new DashboardSummaryDto(
                totalTestCases,
                totalTestPlans,
                totalTestRuns,
                totalBugs,
                activeTestRuns,
                openBugs
        );
    }

    @Override
    public TestRunsByStatusDto getTestRunsByStatus() {
        Map<TestRunStatus, Long> testRunsByStatus = testRunRepository.countByStatus().stream()
                .collect(Collectors.toMap(
                        entry -> (TestRunStatus) entry.get("status"),
                        entry -> (Long) entry.get("count")
                ));
        return new TestRunsByStatusDto(testRunsByStatus);
    }

    @Override
    public BugsBySeverityDto getBugsBySeverity() {
        Map<Severity, Long> bugsBySeverity = bugRepository.countBySeverity().stream()
                .collect(Collectors.toMap(
                        entry -> (Severity) entry.get("severity"),
                        entry -> (Long) entry.get("count")
                ));
        return new BugsBySeverityDto(bugsBySeverity);
    }

    @Override
    public UserStatisticsDto getUserStatistics() {
        long totalUsers = userRepository.count();
        long newUsersToday = userRepository.countByCreatedAtAfter(LocalDate.now().atStartOfDay());
        return new UserStatisticsDto(totalUsers, newUsersToday);
    }
}
