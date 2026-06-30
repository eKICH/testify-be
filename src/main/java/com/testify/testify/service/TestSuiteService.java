package com.testify.testify.service;

import com.testify.testify.dto.TestSuiteCreateRequest;
import com.testify.testify.dto.TestSuiteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TestSuiteService {
    TestSuiteResponse createTestSuite(TestSuiteCreateRequest request, UUID userId);
    TestSuiteResponse getTestSuiteById(Long id);
    Page<TestSuiteResponse> getAllTestSuites(Pageable pageable);
    TestSuiteResponse updateTestSuite(Long id, TestSuiteCreateRequest request, UUID userId);
    void deleteTestSuite(Long id);
}
