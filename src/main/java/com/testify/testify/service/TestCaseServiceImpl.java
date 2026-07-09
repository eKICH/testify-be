package com.testify.testify.service;

import com.testify.testify.dto.TestCaseCreateRequest;
import com.testify.testify.dto.TestCaseResponse;
import com.testify.testify.entity.TestCase;
import com.testify.testify.entity.TestSuite;
import com.testify.testify.entity.Module;
import com.testify.testify.exception.ForbiddenAccessException;
import com.testify.testify.entity.User;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.mapper.TestCaseMapper;
import com.testify.testify.repository.TestCaseRepository;
import com.testify.testify.repository.TestSuiteRepository;
import com.testify.testify.repository.ModuleRepository;
import com.testify.testify.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestCaseServiceImpl implements TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final UserRepository userRepository;
    private final TestSuiteRepository testSuiteRepository;
    private final ModuleRepository moduleRepository;
    private final TestCaseMapper testCaseMapper;

    public TestCaseServiceImpl(TestCaseRepository testCaseRepository, UserRepository userRepository, TestSuiteRepository testSuiteRepository, ModuleRepository moduleRepository, TestCaseMapper testCaseMapper) {
        this.testCaseRepository = testCaseRepository;
        this.userRepository = userRepository;
        this.testSuiteRepository = testSuiteRepository;
        this.moduleRepository = moduleRepository;
        this.testCaseMapper = testCaseMapper;
    }

    @Override
    @Transactional
    public TestCaseResponse createTestCase(TestCaseCreateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        TestSuite testSuite = null;
        if (request.getTestSuiteId() != null) {
            testSuite = testSuiteRepository.findById(request.getTestSuiteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Test Suite not found with id: " + request.getTestSuiteId()));
        }

        Module module = null;
        if (request.getModuleId() != null) {
            module = moduleRepository.findById(request.getModuleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + request.getModuleId()));
        }

        TestCase testCase = testCaseMapper.toTestCase(request);
        testCase.setCreatedBy(user);
        testCase.setTestSuite(testSuite);
        testCase.setModule(module);

        TestCase savedTestCase = testCaseRepository.save(testCase);
        return testCaseMapper.toTestCaseResponse(savedTestCase);
    }

    @Override
    @Transactional
    public List<TestCaseResponse> createBulkTestCases(List<TestCaseCreateRequest> requests, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        // Optimization: If all test cases belong to the same suite, fetch it only once.
        // This assumes the requests list is not empty and all items have the same testSuiteId.
        TestSuite testSuite = null;
        if (requests != null && !requests.isEmpty() && requests.get(0).getTestSuiteId() != null) {
            testSuite = testSuiteRepository.findById(requests.get(0).getTestSuiteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Test Suite not found with id: " + requests.get(0).getTestSuiteId()));
        }
        
        final TestSuite finalTestSuite = testSuite; // Effective final for use in lambda
        List<TestCase> testCases = requests.stream()
                .map(request -> {
                    TestCase testCase = testCaseMapper.toTestCase(request);
                    testCase.setCreatedBy(user);
                    testCase.setTestSuite(finalTestSuite);
                    return testCase;
                })
                .collect(Collectors.toList());

        List<TestCase> savedTestCases = testCaseRepository.saveAll(testCases);
        return savedTestCases.stream()
                .map(testCaseMapper::toTestCaseResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TestCaseResponse getTestCaseById(Long id) {
        TestCase testCase = testCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + id));
        return testCaseMapper.toTestCaseResponse(testCase);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TestCaseResponse> getAllTestCases(Pageable pageable) {
        return testCaseRepository.findAll(pageable)
                .map(testCaseMapper::toTestCaseResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TestCaseResponse> getTestCasesByTestSuite(Long suiteId, Pageable pageable) {
        TestSuite testSuite = testSuiteRepository.findById(suiteId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Suite not found with id: " + suiteId));
        return testCaseRepository.findByTestSuite(testSuite, pageable)
                .map(testCaseMapper::toTestCaseResponse);
    }

    @Override
    @Transactional
    public TestCaseResponse updateTestCase(Long id, TestCaseCreateRequest request, Long userId) {
        TestCase testCase = testCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Authorization check: Ensure the user updating the case is the one who created it.
        if (!testCase.getCreatedBy().getId().equals(userId)) {
            throw new ForbiddenAccessException("You are not authorized to update this test case.");
        }

        TestSuite testSuite = null;
        if (request.getTestSuiteId() != null) {
            testSuite = testSuiteRepository.findById(request.getTestSuiteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Test Suite not found with id: " + request.getTestSuiteId()));
        }

        Module module = null;
        if (request.getModuleId() != null) {
            module = moduleRepository.findById(request.getModuleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + request.getModuleId()));
        }

        testCase.setName(request.getName());
        testCase.setDescription(request.getDescription());
        testCase.setPreconditions(request.getPreconditions());
        testCase.setSteps(request.getSteps());
        testCase.setExpectedResult(request.getExpectedResult());
        testCase.setPriority(request.getPriority());
        testCase.setTestSuite(testSuite);
        testCase.setModule(module);

        TestCase updatedTestCase = testCaseRepository.save(testCase);
        return testCaseMapper.toTestCaseResponse(updatedTestCase);
    }

    @Override
    @Transactional
    public void deleteTestCase(Long id, Long userId) {
        TestCase testCase = testCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + id));

        // Authorization check: Ensure the user deleting the case is the one who created it.
        if (!testCase.getCreatedBy().getId().equals(userId)) {
            throw new ForbiddenAccessException("You are not authorized to delete this test case.");
        }

        testCaseRepository.delete(testCase);
    }
}
