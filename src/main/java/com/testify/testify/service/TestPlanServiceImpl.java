package com.testify.testify.service;

import com.testify.testify.dto.TestCaseResponse;
import com.testify.testify.dto.TestPlanCreateRequest;
import com.testify.testify.dto.TestPlanResponse;
import com.testify.testify.entity.TestCase;
import com.testify.testify.exception.ForbiddenAccessException;
import com.testify.testify.entity.TestPlan;
import com.testify.testify.entity.User;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.mapper.TestCaseMapper;
import com.testify.testify.mapper.TestPlanMapper;
import com.testify.testify.repository.TestCaseRepository;
import com.testify.testify.repository.TestPlanRepository;
import com.testify.testify.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TestPlanServiceImpl implements TestPlanService {

    private final TestPlanRepository testPlanRepository;
    private final UserRepository userRepository;
    private final TestCaseRepository testCaseRepository;
    private final TestPlanMapper testPlanMapper;
    private final TestCaseMapper testCaseMapper;

    public TestPlanServiceImpl(TestPlanRepository testPlanRepository, UserRepository userRepository, TestCaseRepository testCaseRepository, TestPlanMapper testPlanMapper, TestCaseMapper testCaseMapper) {
        this.testPlanRepository = testPlanRepository;
        this.userRepository = userRepository;
        this.testCaseRepository = testCaseRepository;
        this.testPlanMapper = testPlanMapper;
        this.testCaseMapper = testCaseMapper;
    }

    @Override
    @Transactional
    public TestPlanResponse createTestPlan(TestPlanCreateRequest request, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        TestPlan testPlan = testPlanMapper.toTestPlan(request);
        testPlan.setCreatedBy(user);

        TestPlan savedTestPlan = testPlanRepository.save(testPlan);
        return testPlanMapper.toTestPlanResponse(savedTestPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public TestPlanResponse getTestPlanById(Long id) {
        TestPlan testPlan = testPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + id));
        return testPlanMapper.toTestPlanResponse(testPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TestPlanResponse> getAllTestPlans(Pageable pageable) {
        return testPlanRepository.findAll(pageable)
                .map(testPlanMapper::toTestPlanResponse);
    }

    @Override
    @Transactional
    public TestPlanResponse updateTestPlan(Long id, TestPlanCreateRequest request, UUID userId) {
        TestPlan testPlan = testPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Authorization check: Ensure the user updating the plan is the one who created it.
        if (!testPlan.getCreatedBy().getId().equals(userId)) {
            throw new ForbiddenAccessException("You are not authorized to update this test plan.");
        }

        testPlan.setName(request.getName());
        testPlan.setDescription(request.getDescription());
        testPlan.setScope(request.getScope());
        testPlan.setStartDate(request.getStartDate());
        testPlan.setEndDate(request.getEndDate());

        TestPlan updatedTestPlan = testPlanRepository.save(testPlan);
        return testPlanMapper.toTestPlanResponse(updatedTestPlan);
    }

    @Override
    @Transactional
    public void deleteTestPlan(Long id, UUID userId) {
        TestPlan testPlan = testPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + id));

        // Authorization check: Ensure the user deleting the plan is the one who created it.
        if (!testPlan.getCreatedBy().getId().equals(userId)) {
            throw new ForbiddenAccessException("You are not authorized to delete this test plan.");
        }
        testPlanRepository.delete(testPlan);
    }

    @Override
    @Transactional
    public void addTestCaseToTestPlan(Long planId, Long caseId, UUID userId) {
        TestPlan testPlan = testPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + planId));
        TestCase testCase = testCaseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + caseId));

        // Authorization check: Ensure the user modifying the plan is the one who created it.
        if (!testPlan.getCreatedBy().getId().equals(userId)) {
            throw new ForbiddenAccessException("You are not authorized to modify this test plan.");
        }

        testPlan.getTestCases().add(testCase);
        testPlanRepository.save(testPlan);
    }

    @Override
    @Transactional
    public void removeTestCaseFromTestPlan(Long planId, Long caseId, UUID userId) {
        TestPlan testPlan = testPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + planId));
        TestCase testCase = testCaseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + caseId));

        // Authorization check: Ensure the user modifying the plan is the one who created it.
        if (!testPlan.getCreatedBy().getId().equals(userId)) {
            throw new ForbiddenAccessException("You are not authorized to modify this test plan.");
        }

        testPlan.getTestCases().remove(testCase);
        testPlanRepository.save(testPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TestCaseResponse> getTestCasesInPlan(Long planId, Pageable pageable) {
        TestPlan testPlan = testPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + planId));
        return testCaseRepository.findByTestPlans(testPlan, pageable)
                .map(testCaseMapper::toTestCaseResponse);
    }
}
