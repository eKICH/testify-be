package com.testify.testify.controller;

import com.testify.testify.dto.BugCreateRequest;
import com.testify.testify.dto.BugResponse;
import com.testify.testify.entity.BugStatus;
import com.testify.testify.service.BugService;
import com.testify.testify.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/bugs")
@RequiredArgsConstructor
public class BugController {

    private final BugService bugService;

    @PostMapping
    public ResponseEntity<BugResponse> createBug(@RequestBody BugCreateRequest request, @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getId();
        BugResponse response = bugService.createBug(request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<BugResponse>> getAllBugs(Pageable pageable) {
        Page<BugResponse> responses = bugService.getAllBugs(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BugResponse> getBugById(@PathVariable Long id) {
        BugResponse response = bugService.getBugById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BugResponse> updateBug(@PathVariable Long id, @RequestBody BugCreateRequest request,
                                             @AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getId();
        BugResponse response = bugService.updateBug(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBug(@PathVariable Long id) {
        bugService.deleteBug(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BugResponse> updateBugStatus(@PathVariable Long id, @RequestBody String status) {
        BugStatus bugStatus = BugStatus.valueOf(status.toUpperCase());
        BugResponse response = bugService.updateBugStatus(id, bugStatus);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/assign/{assigneeId}")
    public ResponseEntity<BugResponse> assignBug(@PathVariable Long id, @PathVariable Long assigneeId) {
        BugResponse response = bugService.assignBug(id, assigneeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/run/{runId}")
    public ResponseEntity<Page<BugResponse>> getBugsByTestRun(@PathVariable Long runId, Pageable pageable) {
        Page<BugResponse> responses = bugService.getBugsByTestRun(runId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/case/{caseId}")
    public ResponseEntity<Page<BugResponse>> getBugsByTestCase(@PathVariable Long caseId, Pageable pageable) {
        Page<BugResponse> responses = bugService.getBugsByTestCase(caseId, pageable);
        return ResponseEntity.ok(responses);
    }
}
