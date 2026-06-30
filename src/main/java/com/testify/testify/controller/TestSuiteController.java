package com.testify.testify.controller;

import com.testify.testify.dto.TestSuiteCreateRequest;
import com.testify.testify.dto.TestSuiteResponse;
import com.testify.testify.entity.User;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.service.TestSuiteService;
import com.testify.testify.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/test-suites")
public class TestSuiteController {

    private final TestSuiteService testSuiteService;
    private final UserRepository userRepository; // Inject UserRepository

    public TestSuiteController(TestSuiteService testSuiteService, UserRepository userRepository) {
        this.testSuiteService = testSuiteService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<TestSuiteResponse> createTestSuite(@RequestBody TestSuiteCreateRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        // Fetch the user by username from the security principal
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + userDetails.getUsername()));
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
    public ResponseEntity<TestSuiteResponse> updateTestSuite(@PathVariable Long id, @RequestBody TestSuiteCreateRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        // Fetch the user by username from the security principal
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + userDetails.getUsername()));
        UUID userId = user.getId();
        TestSuiteResponse response = testSuiteService.updateTestSuite(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestSuite(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        // Fetch the user by username from the security principal
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + userDetails.getUsername()));
        UUID userId = user.getId();
        testSuiteService.deleteTestSuite(id, userId);
        return ResponseEntity.noContent().build();
    }
}
