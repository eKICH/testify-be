package com.testify.testify.service;

import com.testify.testify.dto.TestCaseCreateRequest;
import com.testify.testify.dto.TestCaseResponse;
import com.testify.testify.entity.TestCase;
import com.testify.testify.entity.TestSuite;
import com.testify.testify.entity.User;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.mapper.TestCaseMapper;
import com.testify.testify.repository.TestCaseRepository;
import com.testify.testify.repository.TestSuiteRepository;
import com.testify.testify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final UserRepository userRepository;
    private final TestSuiteRepository testSuiteRepository;
    private final TestCaseMapper testCaseMapper;

    @Override
    public TestCaseResponse createTestCase(TestCaseCreateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        TestSuite testSuite = null;
        if (request.getTestSuiteId() != null) {
            testSuite = testSuiteRepository.findById(request.getTestSuiteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Test Suite not found with id: " + request.getTestSuiteId()));
        }

        TestCase testCase = testCaseMapper.toTestCase(request);
        testCase.setCreatedBy(user);
        testCase.setTestSuite(testSuite);

        TestCase savedTestCase = testCaseRepository.save(testCase);
        return testCaseMapper.toTestCaseResponse(savedTestCase);
    }

    @Override
    public List<TestCaseResponse> createBulkTestCases(List<TestCaseCreateRequest> requests, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<TestCase> testCases = requests.stream()
                .map(request -> {
                    TestSuite testSuite = null;
                    if (request.getTestSuiteId() != null) {
                        testSuite = testSuiteRepository.findById(request.getTestSuiteId())
                                .orElseThrow(() -> new ResourceNotFoundException("Test Suite not found with id: " + request.getTestSuiteId()));
                    }

                    TestCase testCase = testCaseMapper.toTestCase(request);
                    testCase.setCreatedBy(user);
                    testCase.setTestSuite(testSuite);
                    return testCase;
                })
                .collect(Collectors.toList());

        List<TestCase> savedTestCases = testCaseRepository.saveAll(testCases);
        return savedTestCases.stream()
                .map(testCaseMapper::toTestCaseResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TestCaseResponse getTestCaseById(Long id) {
        TestCase testCase = testCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + id));
        return testCaseMapper.toTestCaseResponse(testCase);
    }

    @Override
    public Page<TestCaseResponse> getAllTestCases(Pageable pageable) {
        return testCaseRepository.findAll(pageable)
                .map(testCaseMapper::toTestCaseResponse);
    }

    @Override
    public Page<TestCaseResponse> getTestCasesByTestSuite(Long suiteId, Pageable pageable) {
        TestSuite testSuite = testSuiteRepository.findById(suiteId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Suite not found with id: " + suiteId));
        return testCaseRepository.findAll(pageable)
                .map(testCaseMapper::toTestCaseResponse);
    }

    @Override
    public TestCaseResponse updateTestCase(Long id, TestCaseCreateRequest request, Long userId) {
        TestCase testCase = testCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        TestSuite testSuite = null;
        if (request.getTestSuiteId() != null) {
            testSuite = testSuiteRepository.findById(request.getTestSuiteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Test Suite not found with id: " + request.getTestSuiteId()));
        }

        testCase.setName(request.getName());
        testCase.setDescription(request.getDescription());
        testCase.setPreconditions(request.getPreconditions());
        testCase.setSteps(request.getSteps());
        testCase.setExpectedResult(request.getExpectedResult());
        testCase.setPriority(request.getPriority());
        testCase.setTestSuite(testSuite);

        TestCase updatedTestCase = testCaseRepository.save(testCase);
        return testCaseMapper.toTestCaseResponse(updatedTestCase);
    }

    @Override
    public void deleteTestCase(Long id) {
        if (!testCaseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Test Case not found with id: " + id);
        }
        testCaseRepository.deleteById(id);
    }
}
