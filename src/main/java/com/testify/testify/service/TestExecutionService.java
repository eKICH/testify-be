package com.testify.testify.service;

import com.testify.testify.dto.TestExecutionRequest;
import com.testify.testify.dto.TestExecutionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestExecutionService {
    TestExecutionResponse recordTestExecution(Long testRunId, TestExecutionRequest request, Long userId);
    TestExecutionResponse updateTestExecution(Long id, TestExecutionRequest request, Long userId);
    Page<TestExecutionResponse> getExecutionsByTestRun(Long runId, Pageable pageable);
}
