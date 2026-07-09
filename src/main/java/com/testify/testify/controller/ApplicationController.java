package com.testify.testify.controller;

import com.testify.testify.dto.ApplicationCreateRequest;
import com.testify.testify.dto.ApplicationResponse;
import com.testify.testify.security.UserPrincipal;
import com.testify.testify.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@RequestBody ApplicationCreateRequest request,
                                                                   @AuthenticationPrincipal UserPrincipal principal) {
        ApplicationResponse response = applicationService.createApplication(request, principal.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> updateApplication(@PathVariable Long id,
                                                                   @RequestBody ApplicationCreateRequest request) {
        return ResponseEntity.ok(applicationService.updateApplication(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id,
                                                   @RequestParam(defaultValue = "false") boolean cascade) {
        applicationService.deleteApplication(id, cascade);
        return ResponseEntity.noContent().build();
    }
}
