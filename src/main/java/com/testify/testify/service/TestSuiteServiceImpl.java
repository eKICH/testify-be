package com.testify.testify.service;

import com.testify.testify.dto.TestSuiteCreateRequest;
import com.testify.testify.dto.TestSuiteResponse;
import com.testify.testify.entity.TestSuite;
import com.testify.testify.entity.User;
import com.testify.testify.exception.ForbiddenAccessException;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.mapper.TestSuiteMapper;
import com.testify.testify.repository.TestSuiteRepository;
import com.testify.testify.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TestSuiteServiceImpl implements TestSuiteService {

    private final TestSuiteRepository testSuiteRepository;
    private final UserRepository userRepository;
    private final TestSuiteMapper testSuiteMapper;

    public TestSuiteServiceImpl(TestSuiteRepository testSuiteRepository, UserRepository userRepository, TestSuiteMapper testSuiteMapper) {
        this.testSuiteRepository = testSuiteRepository;
        this.userRepository = userRepository;
        this.testSuiteMapper = testSuiteMapper;
    }

    @Override
    @Transactional
    public TestSuiteResponse createTestSuite(TestSuiteCreateRequest request, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        TestSuite testSuite = testSuiteMapper.toTestSuite(request);
        testSuite.setCreatedBy(user);

        TestSuite savedTestSuite = testSuiteRepository.save(testSuite);
        return testSuiteMapper.toTestSuiteResponse(savedTestSuite);
    }

    @Override
    @Transactional(readOnly = true)
    public TestSuiteResponse getTestSuiteById(Long id) {
        TestSuite testSuite = testSuiteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Suite not found with id: " + id));
        return testSuiteMapper.toTestSuiteResponse(testSuite);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TestSuiteResponse> getAllTestSuites(Pageable pageable) {
        return testSuiteRepository.findAll(pageable)
                .map(testSuiteMapper::toTestSuiteResponse);
    }

    @Override
    @Transactional
    public TestSuiteResponse updateTestSuite(Long id, TestSuiteCreateRequest request, UUID userId) {
        TestSuite testSuite = testSuiteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Suite not found with id: " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Authorization check: Ensure the user updating the suite is the one who created it.
        if (!testSuite.getCreatedBy().getId().equals(userId)) {
            throw new ForbiddenAccessException("You are not authorized to update this test suite.");
        }

        testSuite.setName(request.getName());
        testSuite.setDescription(request.getDescription());
        // Track who updated the entity
        testSuite.setUpdatedBy(user);

        TestSuite updatedTestSuite = testSuiteRepository.save(testSuite);
        return testSuiteMapper.toTestSuiteResponse(updatedTestSuite);
    }

    @Override
    @Transactional
    public void deleteTestSuite(Long id, UUID userId) {
        TestSuite testSuite = testSuiteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Suite not found with id: " + id));

        // Authorization check: Ensure the user deleting the suite is the one who created it.
        if (!testSuite.getCreatedBy().getId().equals(userId)) {
            throw new ForbiddenAccessException("You are not authorized to delete this test suite.");
        }

        testSuiteRepository.deleteById(id);
    }
}
