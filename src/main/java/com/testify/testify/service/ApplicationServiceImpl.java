package com.testify.testify.service;

import com.testify.testify.entity.Application;
import com.testify.testify.entity.User;
import com.testify.testify.repository.ApplicationRepository;
import com.testify.testify.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Override
    public Application createApplication(String name, String description) {
        applicationRepository.findByName(name).ifPresent(app -> {
            throw new IllegalArgumentException("Application with name '" + name + "' already exists.");
        });

        // In a real app, you would get the current user from the security context.
        // User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Using a placeholder user for now. The original code had a type mismatch (Long vs UUID).
        User currentUser = userRepository.findByUsername("admin")
                .orElseThrow(() -> new EntityNotFoundException("Default 'admin' user not found. Please ensure this user exists."));

        Application newApp = new Application();
        newApp.setName(name);
        newApp.setDescription(description);
        newApp.setCreatedBy(currentUser);

        return applicationRepository.save(newApp);
    }

    @Override
    @Transactional(readOnly = true)
    public Application getApplicationById(UUID id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Application updateApplication(UUID id, String name, String description) {
        Application existingApp = getApplicationById(id);

        // Check if another application with the new name already exists
        applicationRepository.findByName(name).ifPresent(app -> {
            if (!app.getId().equals(id)) {
                throw new IllegalArgumentException("Application with name '" + name + "' already exists.");
            }
        });

        existingApp.setName(name);
        existingApp.setDescription(description);

        return applicationRepository.save(existingApp);
    }

    @Override
    public void deleteApplication(UUID id) {
        if (!applicationRepository.existsById(id)) {
            throw new EntityNotFoundException("Application not found with ID: " + id);
        }
        // Consider cascading deletes or checks for child modules/test cases
        applicationRepository.deleteById(id);
    }
}