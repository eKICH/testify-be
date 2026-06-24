package com.testify.testify.service;

import com.testify.testify.dto.TestCaseResponse;
import com.testify.testify.dto.TestPlanCreateRequest;
import com.testify.testify.dto.TestPlanResponse;
import com.testify.testify.entity.TestCase;
import com.testify.testify.entity.TestPlan;
import com.testify.testify.entity.User;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.mapper.TestCaseMapper;
import com.testify.testify.mapper.TestPlanMapper;
import com.testify.testify.repository.TestCaseRepository;
import com.testify.testify.repository.TestPlanRepository;
import com.testify.testify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestPlanServiceImpl implements TestPlanService {

    private final TestPlanRepository testPlanRepository;
    private final UserRepository userRepository;
    private final TestCaseRepository testCaseRepository;
    private final TestPlanMapper testPlanMapper;
    private final TestCaseMapper testCaseMapper;

    @Override
    public TestPlanResponse createTestPlan(TestPlanCreateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        TestPlan testPlan = testPlanMapper.toTestPlan(request);
        testPlan.setCreatedBy(user);

        TestPlan savedTestPlan = testPlanRepository.save(testPlan);
        return testPlanMapper.toTestPlanResponse(savedTestPlan);
    }

    @Override
    public TestPlanResponse getTestPlanById(Long id) {
        TestPlan testPlan = testPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + id));
        return testPlanMapper.toTestPlanResponse(testPlan);
    }

    @Override
    public Page<TestPlanResponse> getAllTestPlans(Pageable pageable) {
        return testPlanRepository.findAll(pageable)
                .map(testPlanMapper::toTestPlanResponse);
    }

    @Override
    public TestPlanResponse updateTestPlan(Long id, TestPlanCreateRequest request, Long userId) {
        TestPlan testPlan = testPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        testPlan.setName(request.getName());
        testPlan.setDescription(request.getDescription());
        testPlan.setScope(request.getScope());
        testPlan.setStartDate(request.getStartDate());
        testPlan.setEndDate(request.getEndDate());

        TestPlan updatedTestPlan = testPlanRepository.save(testPlan);
        return testPlanMapper.toTestPlanResponse(updatedTestPlan);
    }

    @Override
    public void deleteTestPlan(Long id) {
        if (!testPlanRepository.existsById(id)) {
            throw new ResourceNotFoundException("Test Plan not found with id: " + id);
        }
        testPlanRepository.deleteById(id);
    }

    @Override
    public void addTestCaseToTestPlan(Long planId, Long caseId) {
        TestPlan testPlan = testPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + planId));
        TestCase testCase = testCaseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + caseId));

        testPlan.getTestCases().add(testCase);
        testPlanRepository.save(testPlan);
    }

    @Override
    public void removeTestCaseFromTestPlan(Long planId, Long caseId) {
        TestPlan testPlan = testPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + planId));
        TestCase testCase = testCaseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + caseId));

        testPlan.getTestCases().remove(testCase);
        testPlanRepository.save(testPlan);
    }

    @Override
    public Page<TestCaseResponse> getTestCasesInPlan(Long planId, Pageable pageable) {
        TestPlan testPlan = testPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + planId));
        return new PageImpl<>(
                testPlan.getTestCases().stream()
                        .map(testCaseMapper::toTestCaseResponse)
                        .collect(Collectors.toList()),
                pageable,
                testPlan.getTestCases().size()
        );
    }
}
