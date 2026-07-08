package com.testify.testify.controller;

import com.testify.testify.dto.TestCaseCreateRequest;
import com.testify.testify.dto.TestCaseResponse;
import com.testify.testify.entity.TestCase;
import com.testify.testify.mapper.DtoMapper;
import com.testify.testify.service.TestCaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/test-cases")
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;
    private final DtoMapper mapper;

    @PostMapping
    public ResponseEntity<TestCaseResponse> createTestCase(@Valid @RequestBody TestCaseCreateRequest request) {
        TestCase newTestCase = testCaseService.createTestCase(request);
        return new ResponseEntity<>(mapper.toResponse(newTestCase), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TestCaseResponse>> getAllTestCases() {
        List<TestCaseResponse> responses = testCaseService.getAllTestCases().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCaseResponse> getTestCaseById(@PathVariable UUID id) {
        TestCase testCase = testCaseService.getTestCase(id);
        return ResponseEntity.ok(mapper.toResponse(testCase));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestCaseResponse> updateTestCase(@PathVariable UUID id, @Valid @RequestBody TestCaseCreateRequest request) {
        // Note: This reuses the create request DTO. For more complex updates, a dedicated update DTO might be better.
        // We'll create a temporary TestCase object to pass to the existing update service method.
        TestCase testCaseDetails = new TestCase();
        testCaseDetails.setName(request.getName());
        testCaseDetails.setDescription(request.getDescription());
        testCaseDetails.setPreconditions(request.getPreconditions());
        testCaseDetails.setSteps(request.getSteps());
        testCaseDetails.setExpectedResult(request.getExpectedResult());
        testCaseDetails.setPriority(request.getPriority());
        testCaseDetails.setStatus(request.getStatus());

        TestCase updatedTestCase = testCaseService.updateTestCase(id, testCaseDetails);
        return ResponseEntity.ok(mapper.toResponse(updatedTestCase));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestCase(@PathVariable UUID id) {
        testCaseService.deleteTestCase(id);
        return ResponseEntity.noContent().build();
    }
}
