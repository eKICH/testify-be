package com.testify.testify.service;

import com.testify.testify.dto.TestPlanRequest;
import com.testify.testify.dto.TestPlanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TestPlanService {
    TestPlanResponse createTestPlan(TestPlanRequest request, UUID userId);
    TestPlanResponse getTestPlan(Long id);
    Page<TestPlanResponse> getAllTestPlans(Pageable pageable);
    TestPlanResponse updateTestPlan(Long id, TestPlanRequest request, UUID userId);
    void deleteTestPlan(Long id);
    TestPlanResponse addTestCaseToTestPlan(Long testPlanId, UUID testCaseId, UUID userId);
    TestPlanResponse removeTestCaseFromTestPlan(Long testPlanId, UUID testCaseId, UUID userId);
}

