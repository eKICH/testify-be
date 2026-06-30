package com.testify.testify.service;

import com.testify.testify.dto.TestExecutionRequest;
import com.testify.testify.dto.TestExecutionResponse;
import com.testify.testify.entity.TestCase;
import com.testify.testify.entity.TestExecution;
import com.testify.testify.entity.TestRun;
import com.testify.testify.entity.User;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.mapper.TestExecutionMapper;
import com.testify.testify.repository.TestCaseRepository;
import com.testify.testify.repository.TestExecutionRepository;
import com.testify.testify.repository.TestRunRepository;
import com.testify.testify.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TestExecutionServiceImpl implements TestExecutionService {

    private final TestExecutionRepository testExecutionRepository;
    private final TestRunRepository testRunRepository;
    private final TestCaseRepository testCaseRepository;
    private final UserRepository userRepository;
    private final TestExecutionMapper testExecutionMapper;

    public TestExecutionServiceImpl(TestExecutionRepository testExecutionRepository, TestRunRepository testRunRepository, TestCaseRepository testCaseRepository, UserRepository userRepository, TestExecutionMapper testExecutionMapper) {
        this.testExecutionRepository = testExecutionRepository;
        this.testRunRepository = testRunRepository;
        this.testCaseRepository = testCaseRepository;
        this.userRepository = userRepository;
        this.testExecutionMapper = testExecutionMapper;
    }

    @Override
    public TestExecutionResponse recordTestExecution(Long testRunId, TestExecutionRequest request, UUID userId) {
        TestRun testRun = testRunRepository.findById(testRunId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Run not found with id: " + testRunId));

        TestCase testCase = testCaseRepository.findById(request.getTestCaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + request.getTestCaseId()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        TestExecution testExecution = new TestExecution();
        testExecution.setTestRun(testRun);
        testExecution.setTestCase(testCase);
        testExecution.setStatus(request.getStatus());
        testExecution.setActualResult(request.getActualResult());
        testExecution.setComments(request.getComments());
        testExecution.setExecutedBy(user);
        testExecution.setExecutedAt(LocalDateTime.now());

        TestExecution savedTestExecution = testExecutionRepository.save(testExecution);
        return testExecutionMapper.toTestExecutionResponse(savedTestExecution);
    }

    @Override
    public TestExecutionResponse updateTestExecution(Long id, TestExecutionRequest request, UUID userId) {
        TestExecution testExecution = testExecutionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Execution not found with id: " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        testExecution.setStatus(request.getStatus());
        testExecution.setActualResult(request.getActualResult());
        testExecution.setComments(request.getComments());
        testExecution.setExecutedBy(user);
        testExecution.setExecutedAt(LocalDateTime.now());

        TestExecution updatedTestExecution = testExecutionRepository.save(testExecution);
        return testExecutionMapper.toTestExecutionResponse(updatedTestExecution);
    }

    @Override
    public Page<TestExecutionResponse> getExecutionsByTestRun(Long runId, Pageable pageable) {
        testRunRepository.findById(runId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Run not found with id: " + runId));
        return testExecutionRepository.findByTestRunId(runId, pageable)
                .map(testExecutionMapper::toTestExecutionResponse);
    }
}
