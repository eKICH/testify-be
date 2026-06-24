package com.testify.testify.service;

import com.testify.testify.dto.TestSuiteCreateRequest;
import com.testify.testify.dto.TestSuiteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestSuiteService {
    TestSuiteResponse createTestSuite(TestSuiteCreateRequest request, Long userId);
    TestSuiteResponse getTestSuiteById(Long id);
    Page<TestSuiteResponse> getAllTestSuites(Pageable pageable);
    TestSuiteResponse updateTestSuite(Long id, TestSuiteCreateRequest request, Long userId);
    void deleteTestSuite(Long id);
}
