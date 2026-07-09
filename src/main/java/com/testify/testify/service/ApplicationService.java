package com.testify.testify.service;

import com.testify.testify.dto.ApplicationCreateRequest;
import com.testify.testify.dto.ApplicationResponse;

import java.util.List;

public interface ApplicationService {
    ApplicationResponse createApplication(ApplicationCreateRequest request, Long userId);
    ApplicationResponse getApplicationById(Long id);
    List<ApplicationResponse> getAllApplications();
    ApplicationResponse updateApplication(Long id, ApplicationCreateRequest request);
    void deleteApplication(Long id, boolean cascade);
}
