package com.testify.testify.service;

import com.testify.testify.dto.TestRunCreateRequest;
import com.testify.testify.dto.TestRunResponse;
import com.testify.testify.entity.TestRunStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TestRunService {
    TestRunResponse createTestRun(TestRunCreateRequest request, UUID userId);
    TestRunResponse getTestRunById(Long id);
    Page<TestRunResponse> getAllTestRuns(Pageable pageable);
    Page<TestRunResponse> getTestRunsByTestPlan(Long planId, Pageable pageable);
    TestRunResponse updateTestRun(Long id, TestRunCreateRequest request, UUID userId);
    void deleteTestRun(Long id);
    TestRunResponse updateTestRunStatus(Long id, TestRunStatus status);
}
