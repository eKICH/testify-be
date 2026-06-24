package com.testify.testify.service;

import com.testify.testify.dto.BugCreateRequest;
import com.testify.testify.dto.BugResponse;
import com.testify.testify.entity.*;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.mapper.BugMapper;
import com.testify.testify.repository.BugRepository;
import com.testify.testify.repository.TestCaseRepository;
import com.testify.testify.repository.TestRunRepository;
import com.testify.testify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BugServiceImpl implements BugService {

    private final BugRepository bugRepository;
    private final UserRepository userRepository;
    private final TestCaseRepository testCaseRepository;
    private final TestRunRepository testRunRepository;
    private final BugMapper bugMapper;

    @Override
    public BugResponse createBug(BugCreateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        TestCase testCase = null;
        if (request.getTestCaseId() != null) {
            testCase = testCaseRepository.findById(request.getTestCaseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + request.getTestCaseId()));
        }

        TestRun testRun = null;
        if (request.getTestRunId() != null) {
            testRun = testRunRepository.findById(request.getTestRunId())
                    .orElseThrow(() -> new ResourceNotFoundException("Test Run not found with id: " + request.getTestRunId()));
        }

        Bug bug = bugMapper.toBug(request);
        bug.setReportedBy(user);
        bug.setTestCase(testCase);
        bug.setTestRun(testRun);

        Bug savedBug = bugRepository.save(bug);
        return bugMapper.toBugResponse(savedBug);
    }

    @Override
    public BugResponse getBugById(Long id) {
        Bug bug = bugRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bug not found with id: " + id));
        return bugMapper.toBugResponse(bug);
    }

    @Override
    public Page<BugResponse> getAllBugs(Pageable pageable) {
        return bugRepository.findAll(pageable)
                .map(bugMapper::toBugResponse);
    }

    @Override
    public BugResponse updateBug(Long id, BugCreateRequest request, Long userId) {
        Bug bug = bugRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bug not found with id: " + id));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        TestCase testCase = null;
        if (request.getTestCaseId() != null) {
            testCase = testCaseRepository.findById(request.getTestCaseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + request.getTestCaseId()));
        }

        TestRun testRun = null;
        if (request.getTestRunId() != null) {
            testRun = testRunRepository.findById(request.getTestRunId())
                    .orElseThrow(() -> new ResourceNotFoundException("Test Run not found with id: " + request.getTestRunId()));
        }

        bug.setTitle(request.getTitle());
        bug.setDescription(request.getDescription());
        bug.setSeverity(request.getSeverity());
        bug.setPriority(request.getPriority());
        bug.setStepsToReproduce(request.getStepsToReproduce());
        bug.setExpectedBehavior(request.getExpectedBehavior());
        bug.setActualBehavior(request.getActualBehavior());
        bug.setEnvironment(request.getEnvironment());
        bug.setTestCase(testCase);
        bug.setTestRun(testRun);

        Bug updatedBug = bugRepository.save(bug);
        return bugMapper.toBugResponse(updatedBug);
    }

    @Override
    public void deleteBug(Long id) {
        if (!bugRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bug not found with id: " + id);
        }
        bugRepository.deleteById(id);
    }

    @Override
    public BugResponse updateBugStatus(Long id, BugStatus status) {
        Bug bug = bugRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bug not found with id: " + id));
        bug.setStatus(status);
        Bug updatedBug = bugRepository.save(bug);
        return bugMapper.toBugResponse(updatedBug);
    }

    @Override
    public BugResponse assignBug(Long id, Long assigneeId) {
        Bug bug = bugRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bug not found with id: " + id));
        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + assigneeId));
        bug.setAssignedTo(assignee);
        Bug updatedBug = bugRepository.save(bug);
        return bugMapper.toBugResponse(updatedBug);
    }

    @Override
    public Page<BugResponse> getBugsByTestRun(Long runId, Pageable pageable) {
        TestRun testRun = testRunRepository.findById(runId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Run not found with id: " + runId));
        return bugRepository.findAll(pageable)
                .map(bugMapper::toBugResponse);
    }

    @Override
    public Page<BugResponse> getBugsByTestCase(Long caseId, Pageable pageable) {
        TestCase testCase = testCaseRepository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Case not found with id: " + caseId));
        return bugRepository.findAll(pageable)
                .map(bugMapper::toBugResponse);
    }
}
