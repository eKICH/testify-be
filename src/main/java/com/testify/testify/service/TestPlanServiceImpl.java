package com.testify.testify.service;

import com.testify.testify.dto.TestPlanRequest;
import com.testify.testify.dto.TestPlanResponse;
import com.testify.testify.entity.TestCase;
import com.testify.testify.entity.TestPlan;
import com.testify.testify.entity.User;
import com.testify.testify.exception.ForbiddenAccessException;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.mapper.TestPlanMapper;
import com.testify.testify.repository.TestCaseRepository;
import com.testify.testify.repository.TestPlanRepository;
import com.testify.testify.repository.UserRepository;
import org.springframework.data.domain.Page;
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

    public TestPlanServiceImpl(TestPlanRepository testPlanRepository, UserRepository userRepository, TestCaseRepository testCaseRepository, TestPlanMapper testPlanMapper) {
        this.testPlanRepository = testPlanRepository;
        this.userRepository = userRepository;
        this.testCaseRepository = testCaseRepository;
        this.testPlanMapper = testPlanMapper;
    }

    @Override
    @Transactional
    public TestPlanResponse createTestPlan(TestPlanRequest request, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        TestPlan testPlan = testPlanMapper.toTestPlan(request);
        testPlan.setCreatedBy(user);
        TestPlan savedTestPlan = testPlanRepository.save(testPlan);
        return testPlanMapper.toTestPlanResponse(savedTestPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public TestPlanResponse getTestPlan(Long id) {
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
    public TestPlanResponse updateTestPlan(Long id, TestPlanRequest request, UUID userId) {
        TestPlan testPlan = testPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + id));

        if (!testPlan.getCreatedBy().getId().equals(userId)) {
            throw new ForbiddenAccessException("You are not authorized to update this test plan.");
        }

        testPlan.setName(request.getName());
        testPlan.setDescription(request.getDescription());
        TestPlan updatedTestPlan = testPlanRepository.save(testPlan);
        return testPlanMapper.toTestPlanResponse(updatedTestPlan);
    }

    @Override
    @Transactional
    public void deleteTestPlan(Long id) {
        if (!testPlanRepository.existsById(id)) {
            throw new ResourceNotFoundException("Test Plan not found with id: " + id);
        }
        testPlanRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TestPlanResponse addTestCaseToTestPlan(Long testPlanId, UUID testCaseId, UUID userId) {
        TestPlan testPlan = testPlanRepository.findById(testPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + testPlanId));
        TestCase testCase = testCaseRepository.findById(testCaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + testCaseId));

        if (!testPlan.getCreatedBy().getId().equals(userId)) {
            throw new ForbiddenAccessException("You are not authorized to modify this test plan.");
        }

        testPlan.getTestCases().add(testCase);
        TestPlan updatedTestPlan = testPlanRepository.save(testPlan);
        return testPlanMapper.toTestPlanResponse(updatedTestPlan);
    }

    @Override
    @Transactional
    public TestPlanResponse removeTestCaseFromTestPlan(Long testPlanId, UUID testCaseId, UUID userId) {
        TestPlan testPlan = testPlanRepository.findById(testPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + testPlanId));

        if (!testPlan.getCreatedBy().getId().equals(userId)) {
            throw new ForbiddenAccessException("You are not authorized to modify this test plan.");
        }

        testPlan.getTestCases().removeIf(testCase -> testCase.getId().equals(testCaseId));
        TestPlan updatedTestPlan = testPlanRepository.save(testPlan);
        return testPlanMapper.toTestPlanResponse(updatedTestPlan);
    }
}
