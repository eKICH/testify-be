package com.testify.testify.service;

import com.testify.testify.dto.BugCreateRequest;
import com.testify.testify.dto.BugResponse;
import com.testify.testify.entity.BugStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BugService {
    BugResponse createBug(BugCreateRequest request, UUID userId);
    BugResponse getBugById(Long id);
    Page<BugResponse> getAllBugs(Pageable pageable);
    BugResponse updateBug(Long id, BugCreateRequest request, UUID userId);
    void deleteBug(Long id);
    BugResponse updateBugStatus(Long id, BugStatus status);
    BugResponse assignBug(Long id, UUID assigneeId);
    Page<BugResponse> getBugsByTestRun(Long runId, Pageable pageable);
    Page<BugResponse> getBugsByTestCase(Long caseId, Pageable pageable);
}
