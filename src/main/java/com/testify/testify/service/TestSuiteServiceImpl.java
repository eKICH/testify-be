package com.testify.testify.service;

import com.testify.testify.dto.TestSuiteCreateRequest;
import com.testify.testify.dto.TestSuiteResponse;
import com.testify.testify.entity.TestSuite;
import com.testify.testify.entity.User;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.mapper.TestSuiteMapper;
import com.testify.testify.repository.TestSuiteRepository;
import com.testify.testify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestSuiteServiceImpl implements TestSuiteService {

    private final TestSuiteRepository testSuiteRepository;
    private final UserRepository userRepository;
    private final TestSuiteMapper testSuiteMapper;

    @Override
    public TestSuiteResponse createTestSuite(TestSuiteCreateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        TestSuite testSuite = testSuiteMapper.toTestSuite(request);
        testSuite.setCreatedBy(user);

        TestSuite savedTestSuite = testSuiteRepository.save(testSuite);
        return testSuiteMapper.toTestSuiteResponse(savedTestSuite);
    }

    @Override
    public TestSuiteResponse getTestSuiteById(Long id) {
        TestSuite testSuite = testSuiteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Suite not found with id: " + id));
        return testSuiteMapper.toTestSuiteResponse(testSuite);
    }

    @Override
    public Page<TestSuiteResponse> getAllTestSuites(Pageable pageable) {
        return testSuiteRepository.findAll(pageable)
                .map(testSuiteMapper::toTestSuiteResponse);
    }

    @Override
    public TestSuiteResponse updateTestSuite(Long id, TestSuiteCreateRequest request, Long userId) {
        TestSuite testSuite = testSuiteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Suite not found with id: " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        testSuite.setName(request.getName());
        testSuite.setDescription(request.getDescription());

        TestSuite updatedTestSuite = testSuiteRepository.save(testSuite);
        return testSuiteMapper.toTestSuiteResponse(updatedTestSuite);
    }

    @Override
    public void deleteTestSuite(Long id) {
        if (!testSuiteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Test Suite not found with id: " + id);
        }
        testSuiteRepository.deleteById(id);
    }
}
