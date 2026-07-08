package com.testify.testify.controller;

import com.testify.testify.dto.ApplicationRequest;
import com.testify.testify.dto.ApplicationResponse;
import jakarta.validation.Valid;
import com.testify.testify.mapper.DtoMapper;
import com.testify.testify.entity.Application;
import com.testify.testify.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final DtoMapper mapper;

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@Valid @RequestBody ApplicationRequest request) {
        Application application = applicationService.createApplication(request.getName(), request.getDescription());
        return new ResponseEntity<>(mapper.toResponse(application), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getApplicationById(@PathVariable UUID id) {
        Application application = applicationService.getApplicationById(id);
        return ResponseEntity.ok(mapper.toResponse(application));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {
        List<ApplicationResponse> responses = applicationService.getAllApplications().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> updateApplication(@PathVariable UUID id, @Valid @RequestBody ApplicationRequest request) {
        Application updatedApplication = applicationService.updateApplication(id, request.getName(), request.getDescription());
        return ResponseEntity.ok(mapper.toResponse(updatedApplication));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable UUID id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}