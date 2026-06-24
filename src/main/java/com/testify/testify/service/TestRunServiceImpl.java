package com.testify.testify.service;

import com.testify.testify.dto.TestRunCreateRequest;
import com.testify.testify.dto.TestRunResponse;
import com.testify.testify.entity.TestPlan;
import com.testify.testify.entity.TestRun;
import com.testify.testify.entity.TestRunStatus;
import com.testify.testify.entity.User;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.mapper.TestRunMapper;
import com.testify.testify.repository.TestPlanRepository;
import com.testify.testify.repository.TestRunRepository;
import com.testify.testify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestRunServiceImpl implements TestRunService {

    private final TestRunRepository testRunRepository;
    private final UserRepository userRepository;
    private final TestPlanRepository testPlanRepository;
    private final TestRunMapper testRunMapper;

    @Override
    public TestRunResponse createTestRun(TestRunCreateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        TestPlan testPlan = testPlanRepository.findById(request.getTestPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + request.getTestPlanId()));

        TestRun testRun = testRunMapper.toTestRun(request);
        testRun.setCreatedBy(user);
        testRun.setTestPlan(testPlan);

        TestRun savedTestRun = testRunRepository.save(testRun);
        return testRunMapper.toTestRunResponse(savedTestRun);
    }

    @Override
    public TestRunResponse getTestRunById(Long id) {
        TestRun testRun = testRunRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Run not found with id: " + id));
        return testRunMapper.toTestRunResponse(testRun);
    }

    @Override
    public Page<TestRunResponse> getAllTestRuns(Pageable pageable) {
        return testRunRepository.findAll(pageable)
                .map(testRunMapper::toTestRunResponse);
    }

    @Override
    public Page<TestRunResponse> getTestRunsByTestPlan(Long planId, Pageable pageable) {
        TestPlan testPlan = testPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + planId));
        return testRunRepository.findAll(pageable)
                .map(testRunMapper::toTestRunResponse);
    }

    @Override
    public TestRunResponse updateTestRun(Long id, TestRunCreateRequest request, Long userId) {
        TestRun testRun = testRunRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Run not found with id: " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        TestPlan testPlan = testPlanRepository.findById(request.getTestPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Test Plan not found with id: " + request.getTestPlanId()));

        testRun.setName(request.getName());
        testRun.setDescription(request.getDescription());
        testRun.setBuildVersion(request.getBuildVersion());
        testRun.setEnvironment(request.getEnvironment());
        testRun.setTestPlan(testPlan);

        TestRun updatedTestRun = testRunRepository.save(testRun);
        return testRunMapper.toTestRunResponse(updatedTestRun);
    }

    @Override
    public void deleteTestRun(Long id) {
        if (!testRunRepository.existsById(id)) {
            throw new ResourceNotFoundException("Test Run not found with id: " + id);
        }
        testRunRepository.deleteById(id);
    }

    @Override
    public TestRunResponse updateTestRunStatus(Long id, TestRunStatus status) {
        TestRun testRun = testRunRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Run not found with id: " + id));
        testRun.setStatus(status);
        TestRun updatedTestRun = testRunRepository.save(testRun);
        return testRunMapper.toTestRunResponse(updatedTestRun);
    }
}
