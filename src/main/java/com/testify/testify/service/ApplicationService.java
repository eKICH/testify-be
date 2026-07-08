package com.testify.testify.service;

import com.testify.testify.entity.Application;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing Application entities.
 */
public interface ApplicationService {

    Application createApplication(String name, String description);

    Application getApplicationById(UUID id);

    List<Application> getAllApplications();

    Application updateApplication(UUID id, String name, String description);

    void deleteApplication(UUID id);
}