package com.testify.testify.service;

import com.testify.testify.dto.TestCaseCreateRequest;
import com.testify.testify.entity.Module;
import com.testify.testify.entity.TestCase;
import com.testify.testify.entity.User;
import com.testify.testify.repository.ModuleRepository;
import com.testify.testify.repository.TestCaseRepository;
import com.testify.testify.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TestCaseServiceImpl implements TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;

    @Override
    public TestCase createTestCase(TestCaseCreateRequest request) {
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new EntityNotFoundException("Module not found with ID: " + request.getModuleId()));

        // In a real app, you would get the current user from the security context
        User currentUser = userRepository.findByUsername("admin")
                .orElseThrow(() -> new EntityNotFoundException("Default 'admin' user not found."));

        TestCase newTestCase = new TestCase();
        newTestCase.setName(request.getName());
        newTestCase.setDescription(request.getDescription());
        newTestCase.setPreconditions(request.getPreconditions());
        newTestCase.setSteps(request.getSteps());
        newTestCase.setExpectedResult(request.getExpectedResult());
        newTestCase.setPriority(request.getPriority());
        newTestCase.setStatus(request.getStatus());

        // Associate the test case with the module
        newTestCase.setModule(module);
        newTestCase.setCreatedBy(currentUser);

        return testCaseRepository.save(newTestCase);
    }

    @Override
    @Transactional(readOnly = true)
    public TestCase getTestCase(UUID id) {
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TestCase not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestCase> getAllTestCases() {
        return testCaseRepository.findAll();
    }

    @Override
    public TestCase updateTestCase(UUID id, TestCase testCaseDetails) {
        TestCase existingTestCase = getTestCase(id);
        // Note: This is a simple update. A more robust implementation would use a DTO.
        existingTestCase.setName(testCaseDetails.getName());
        existingTestCase.setDescription(testCaseDetails.getDescription());
        existingTestCase.setPreconditions(testCaseDetails.getPreconditions());
        existingTestCase.setSteps(testCaseDetails.getSteps());
        existingTestCase.setExpectedResult(testCaseDetails.getExpectedResult());
        existingTestCase.setPriority(testCaseDetails.getPriority());
        existingTestCase.setStatus(testCaseDetails.getStatus());
        return testCaseRepository.save(existingTestCase);
    }

    @Override
    public void deleteTestCase(UUID id) {
        if (!testCaseRepository.existsById(id)) {
            throw new EntityNotFoundException("TestCase not found with ID: " + id);
        }
        testCaseRepository.deleteById(id);
    }
}
