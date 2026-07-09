package com.testify.testify.controller;

import com.testify.testify.dto.TestCaseResponse;
import com.testify.testify.dto.TestPlanCreateRequest;
import com.testify.testify.dto.TestPlanResponse;
import com.testify.testify.security.UserPrincipal;
import com.testify.testify.service.TestPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test-plans")
@RequiredArgsConstructor
public class TestPlanController {

    private final TestPlanService testPlanService;

    @PostMapping
    public ResponseEntity<TestPlanResponse> createTestPlan(@RequestBody TestPlanCreateRequest request,
                                                             @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getId();
        TestPlanResponse response = testPlanService.createTestPlan(request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TestPlanResponse>> getAllTestPlans(Pageable pageable) {
        Page<TestPlanResponse> responses = testPlanService.getAllTestPlans(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestPlanResponse> getTestPlanById(@PathVariable Long id) {
        TestPlanResponse response = testPlanService.getTestPlanById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestPlanResponse> updateTestPlan(@PathVariable Long id, @RequestBody TestPlanCreateRequest request,
                                                             @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getId();
        TestPlanResponse response = testPlanService.updateTestPlan(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestPlan(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getId();
        testPlanService.deleteTestPlan(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/test-cases/{caseId}")
    public ResponseEntity<Void> addTestCaseToTestPlan(@PathVariable Long id, @PathVariable Long caseId,
                                                        @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getId();
        testPlanService.addTestCaseToTestPlan(id, caseId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/test-cases/{caseId}")
    public ResponseEntity<Void> removeTestCaseFromTestPlan(@PathVariable Long id, @PathVariable Long caseId,
                                                             @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getId();
        testPlanService.removeTestCaseFromTestPlan(id, caseId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/test-cases")
    public ResponseEntity<Page<TestCaseResponse>> getTestCasesInPlan(@PathVariable Long id, Pageable pageable) {
        Page<TestCaseResponse> responses = testPlanService.getTestCasesInPlan(id, pageable);
        return ResponseEntity.ok(responses);
    }
}
