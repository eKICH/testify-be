package com.testify.testify.controller;

import com.testify.testify.dto.TestCaseCreateRequest;
import com.testify.testify.dto.TestCaseResponse;
import com.testify.testify.service.TestCaseService;
import com.testify.testify.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test-cases")
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;

    @PostMapping
    public ResponseEntity<TestCaseResponse> createTestCase(@RequestBody TestCaseCreateRequest request,
                                                       @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getId();
        TestCaseResponse response = testCaseService.createTestCase(request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/bulk-create")
    public ResponseEntity<List<TestCaseResponse>> createBulkTestCases(@RequestBody List<TestCaseCreateRequest> requests,
                                                                @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getId();
        List<TestCaseResponse> responses = testCaseService.createBulkTestCases(requests, userId);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TestCaseResponse>> getAllTestCases(Pageable pageable) {
        Page<TestCaseResponse> responses = testCaseService.getAllTestCases(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCaseResponse> getTestCaseById(@PathVariable Long id) {
        TestCaseResponse response = testCaseService.getTestCaseById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestCaseResponse> updateTestCase(@PathVariable Long id, @RequestBody TestCaseCreateRequest request,
                                                       @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getId();
        TestCaseResponse response = testCaseService.updateTestCase(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestCase(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getId();
        testCaseService.deleteTestCase(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/suite/{suiteId}")
    public ResponseEntity<Page<TestCaseResponse>> getTestCasesByTestSuite(@PathVariable Long suiteId, Pageable pageable) {
        Page<TestCaseResponse> responses = testCaseService.getTestCasesByTestSuite(suiteId, pageable);
        return ResponseEntity.ok(responses);
    }
}
