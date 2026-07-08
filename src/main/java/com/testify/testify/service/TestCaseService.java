package com.testify.testify.service;

import com.testify.testify.dto.TestCaseCreateRequest;
import com.testify.testify.entity.TestCase;

import java.util.List;
import java.util.UUID;

public interface TestCaseService {
    TestCase createTestCase(TestCaseCreateRequest request);
    TestCase getTestCase(UUID id);
    List<TestCase> getAllTestCases();
    TestCase updateTestCase(UUID id, TestCase testCase);
    void deleteTestCase(UUID id);
}
