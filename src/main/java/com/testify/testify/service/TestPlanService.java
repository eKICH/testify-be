package com.testify.testify.service;

import com.testify.testify.dto.TestCaseResponse;
import com.testify.testify.dto.TestPlanCreateRequest;
import com.testify.testify.dto.TestPlanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TestPlanService {
    TestPlanResponse createTestPlan(TestPlanCreateRequest request, UUID userId);
    TestPlanResponse getTestPlanById(Long id);
    Page<TestPlanResponse> getAllTestPlans(Pageable pageable);
    TestPlanResponse updateTestPlan(Long id, TestPlanCreateRequest request, UUID userId);
    void deleteTestPlan(Long id, UUID userId);
    void addTestCaseToTestPlan(Long planId, Long caseId, UUID userId);
    void removeTestCaseFromTestPlan(Long planId, Long caseId, UUID userId);
    Page<TestCaseResponse> getTestCasesInPlan(Long planId, Pageable pageable);
}
