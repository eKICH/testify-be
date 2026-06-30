package com.testify.testify.service;

import com.testify.testify.dto.TestCaseCreateRequest;
import com.testify.testify.dto.TestCaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TestCaseService {
    TestCaseResponse createTestCase(TestCaseCreateRequest request, UUID userId);
    List<TestCaseResponse> createBulkTestCases(List<TestCaseCreateRequest> requests, UUID userId);
    TestCaseResponse getTestCaseById(Long id);
    Page<TestCaseResponse> getAllTestCases(Pageable pageable);
    Page<TestCaseResponse> getTestCasesByTestSuite(Long suiteId, Pageable pageable);
    TestCaseResponse updateTestCase(Long id, TestCaseCreateRequest request, UUID userId);
    void deleteTestCase(Long id, UUID userId);
}