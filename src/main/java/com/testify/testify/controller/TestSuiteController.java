package com.testify.testify.controller;

import com.testify.testify.dto.TestSuiteCreateRequest;
import com.testify.testify.dto.TestSuiteResponse;
import com.testify.testify.service.TestSuiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test-suites")
@RequiredArgsConstructor
public class TestSuiteController {

    private final TestSuiteService testSuiteService;

    @PostMapping
    public ResponseEntity<TestSuiteResponse> createTestSuite(@RequestBody TestSuiteCreateRequest request) {
        // Assuming the user ID is retrieved from the security context
        Long userId = 1L; // Placeholder
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
        // Assuming the user ID is retrieved from the security context
        Long userId = 1L; // Placeholder
        TestSuiteResponse response = testSuiteService.updateTestSuite(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestSuite(@PathVariable Long id) {
        testSuiteService.deleteTestSuite(id);
        return ResponseEntity.noContent().build();
    }
}
