package com.testify.testify.service;

import com.testify.testify.dto.ApplicationCreateRequest;
import com.testify.testify.dto.ApplicationResponse;
import com.testify.testify.dto.UserDto;
import com.testify.testify.entity.Application;
import com.testify.testify.entity.Module;
import com.testify.testify.entity.User;
import com.testify.testify.exception.ConflictException;
import com.testify.testify.exception.DuplicateResourceException;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.repository.ApplicationRepository;
import com.testify.testify.repository.ModuleRepository;
import com.testify.testify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final ModuleService moduleService;

    @Override
    @Transactional
    public ApplicationResponse createApplication(ApplicationCreateRequest request, Long userId) {
        if (applicationRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("An application named '" + request.getName() + "' already exists");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Application application = new Application();
        application.setName(request.getName());
        application.setDescription(request.getDescription());
        application.setCreatedBy(user);

        Application saved = applicationRepository.save(application);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        return toResponse(application);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ApplicationResponse updateApplication(Long id, ApplicationCreateRequest request) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));

        if (!application.getName().equals(request.getName()) && applicationRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("An application named '" + request.getName() + "' already exists");
        }

        application.setName(request.getName());
        application.setDescription(request.getDescription());

        Application saved = applicationRepository.save(application);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteApplication(Long id, boolean cascade) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));

        List<Module> topLevelModules = moduleRepository.findByApplicationIdAndParentModuleIsNullOrderByName(id);
        boolean hasModules = !topLevelModules.isEmpty();

        if (hasModules && !cascade) {
            throw new ConflictException(
                    "Application '" + application.getName() + "' still has modules. " +
                    "Move or delete them first, or retry with ?cascade=true.");
        }

        // Reuse ModuleService's deletion, which already handles subtree ordering
        // and detaching (not deleting) any test cases so history is preserved.
        for (Module topLevelModule : topLevelModules) {
            moduleService.deleteModule(topLevelModule.getId(), true);
        }

        applicationRepository.delete(application);
    }

    private ApplicationResponse toResponse(Application application) {
        ApplicationResponse response = new ApplicationResponse();
        response.setId(application.getId());
        response.setName(application.getName());
        response.setDescription(application.getDescription());
        response.setModuleCount(moduleRepository.findByApplicationIdAndParentModuleIsNullOrderByName(application.getId()).size());

        if (application.getCreatedBy() != null) {
            User creator = application.getCreatedBy();
            response.setCreatedBy(new UserDto(creator.getId(), creator.getUsername(), creator.getFirstName(), creator.getLastName()));
        }

        response.setCreatedAt(application.getCreatedAt());
        response.setUpdatedAt(application.getUpdatedAt());
        return response;
    }
}
