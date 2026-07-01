package com.testify.testify.controller;

import com.testify.testify.dto.TestSuiteCreateRequest;
import com.testify.testify.dto.TestSuiteResponse;
import com.testify.testify.entity.User;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.service.TestSuiteService;
import com.testify.testify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/test-suites")
@RequiredArgsConstructor
public class TestSuiteController {

    private final TestSuiteService testSuiteService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TestSuiteResponse> createTestSuite(@RequestBody TestSuiteCreateRequest request) {
        // TEMPORARY: Hardcode user for testing
        User user = userRepository.findByUsername("user")
                .orElseThrow(() -> new ResourceNotFoundException("Default user 'user' not found for testing"));
        UUID userId = user.getId();
        TestSuiteResponse response = testSuiteService.createTestSuite(request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TestSuiteResponse>> getAllTestSuites(Pageable pageable) {
        Page<TestSuiteResponse> responses = testSuiteService.getAllTestSuites(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestSuiteResponse> getTestSuiteById(@PathVariable Long id) {
        TestSuiteResponse response = testSuiteService.getTestSuiteById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestSuiteResponse> updateTestSuite(@PathVariable Long id, @RequestBody TestSuiteCreateRequest request) {
        // TEMPORARY: Hardcode user for testing
        User user = userRepository.findByUsername("user")
                .orElseThrow(() -> new ResourceNotFoundException("Default user 'user' not found for testing"));
        UUID userId = user.getId();
        TestSuiteResponse response = testSuiteService.updateTestSuite(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestSuite(@PathVariable Long id) {
        // TEMPORARY: Hardcode user for testing
        User user = userRepository.findByUsername("user")
                .orElseThrow(() -> new ResourceNotFoundException("Default user 'user' not found for testing"));
        UUID userId = user.getId();
        testSuiteService.deleteTestSuite(id, userId);
        return ResponseEntity.noContent().build();
    }
}