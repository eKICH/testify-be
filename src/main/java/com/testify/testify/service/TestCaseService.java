package com.testify.testify.service;

import com.testify.testify.dto.TestCaseCreateRequest;
import com.testify.testify.dto.TestCaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestCaseService {
    TestCaseResponse createTestCase(TestCaseCreateRequest request, Long userId);
    List<TestCaseResponse> createBulkTestCases(List<TestCaseCreateRequest> requests, Long userId);
    TestCaseResponse getTestCaseById(Long id);
    Page<TestCaseResponse> getAllTestCases(Pageable pageable);
    Page<TestCaseResponse> getTestCasesByTestSuite(Long suiteId, Pageable pageable);
    TestCaseResponse updateTestCase(Long id, TestCaseCreateRequest request, Long userId);
    void deleteTestCase(Long id);
}
