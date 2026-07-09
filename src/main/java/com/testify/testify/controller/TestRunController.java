package com.testify.testify.controller;

import com.testify.testify.dto.TestRunCreateRequest;
import com.testify.testify.dto.TestRunResponse;
import com.testify.testify.entity.TestRunStatus;
import com.testify.testify.security.UserPrincipal;
import com.testify.testify.service.TestRunService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/test-runs")
public class TestRunController {

    private final TestRunService testRunService;

    public TestRunController(TestRunService testRunService) {
        this.testRunService = testRunService;
    }

    @PostMapping
    public ResponseEntity<TestRunResponse> createTestRun(@RequestBody TestRunCreateRequest request, @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getId();
        TestRunResponse response = testRunService.createTestRun(request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TestRunResponse>> getAllTestRuns(Pageable pageable) {
        Page<TestRunResponse> responses = testRunService.getAllTestRuns(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestRunResponse> getTestRunById(@PathVariable Long id) {
        TestRunResponse response = testRunService.getTestRunById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestRunResponse> updateTestRun(@PathVariable Long id, @RequestBody TestRunCreateRequest request,
                                                     @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getId();
        TestRunResponse response = testRunService.updateTestRun(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestRun(@PathVariable Long id) {
        testRunService.deleteTestRun(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TestRunResponse> updateTestRunStatus(@PathVariable Long id, @RequestBody String status) {
        TestRunStatus runStatus = TestRunStatus.valueOf(status.toUpperCase());
        TestRunResponse response = testRunService.updateTestRunStatus(id, runStatus);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/plan/{planId}")
    public ResponseEntity<Page<TestRunResponse>> getTestRunsByTestPlan(@PathVariable Long planId, Pageable pageable) {
        Page<TestRunResponse> responses = testRunService.getTestRunsByTestPlan(planId, pageable);
        return ResponseEntity.ok(responses);
    }
}
