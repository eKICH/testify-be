package com.testify.testify.service;

import com.testify.testify.dto.BugCreateRequest;
import com.testify.testify.dto.BugResponse;
import com.testify.testify.entity.BugStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BugService {
    BugResponse createBug(BugCreateRequest request, Long userId);
    BugResponse getBugById(Long id);
    Page<BugResponse> getAllBugs(Pageable pageable);
    BugResponse updateBug(Long id, BugCreateRequest request, Long userId);
    void deleteBug(Long id);
    BugResponse updateBugStatus(Long id, BugStatus status);
    BugResponse assignBug(Long id, Long assigneeId);
    Page<BugResponse> getBugsByTestRun(Long runId, Pageable pageable);
    Page<BugResponse> getBugsByTestCase(Long caseId, Pageable pageable);
}
