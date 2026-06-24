package com.testify.testify.controller;

import com.testify.testify.dto.TestExecutionRequest;
import com.testify.testify.dto.TestExecutionResponse;
import com.testify.testify.service.TestExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test-executions")
@RequiredArgsConstructor
public class TestExecutionController {

    private final TestExecutionService testExecutionService;

    @PostMapping("/run/{testRunId}")
    public ResponseEntity<TestExecutionResponse> recordTestExecution(@PathVariable Long testRunId, @RequestBody TestExecutionRequest request) {
        // Assuming the user ID is retrieved from the security context
        Long userId = 1L; // Placeholder
        TestExecutionResponse response = testExecutionService.recordTestExecution(testRunId, request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestExecutionResponse> updateTestExecution(@PathVariable Long id, @RequestBody TestExecutionRequest request) {
        // Assuming the user ID is retrieved from the security context
        Long userId = 1L; // Placeholder
        TestExecutionResponse response = testExecutionService.updateTestExecution(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/run/{runId}")
    public ResponseEntity<Page<TestExecutionResponse>> getExecutionsByTestRun(@PathVariable Long runId, Pageable pageable) {
        Page<TestExecutionResponse> responses = testExecutionService.getExecutionsByTestRun(runId, pageable);
        return ResponseEntity.ok(responses);
    }
}
