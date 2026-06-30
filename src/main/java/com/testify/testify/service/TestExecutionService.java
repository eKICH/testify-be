package com.testify.testify.service;

import com.testify.testify.dto.TestExecutionRequest;
import com.testify.testify.dto.TestExecutionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TestExecutionService {
    TestExecutionResponse recordTestExecution(Long testRunId, TestExecutionRequest request, UUID userId);
    TestExecutionResponse updateTestExecution(Long id, TestExecutionRequest request, UUID userId);
    Page<TestExecutionResponse> getExecutionsByTestRun(Long runId, Pageable pageable);
}
