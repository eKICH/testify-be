package com.testify.testify.service;

import com.testify.testify.dto.ModuleRequest;
import com.testify.testify.entity.Application;
import com.testify.testify.entity.Module;
import com.testify.testify.entity.User;
import com.testify.testify.repository.ApplicationRepository;
import com.testify.testify.repository.ModuleRepository;
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
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Override
    public Module createModule(ModuleRequest request) {
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new EntityNotFoundException("Application not found with ID: " + request.getApplicationId()));

        // In a real app, you would get the current user from the security context.
        User currentUser = userRepository.findByUsername("admin")
                .orElseThrow(() -> new EntityNotFoundException("Default 'admin' user not found. Please ensure this user exists."));

        Module newModule = new Module();
        newModule.setName(request.getName());
        newModule.setDescription(request.getDescription());
        newModule.setApplication(application);
        newModule.setCreatedBy(currentUser);

        String path;
        int depth;

        if (request.getParentModuleId() != null) {
            Module parentModule = moduleRepository.findById(request.getParentModuleId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent module not found with ID: " + request.getParentModuleId()));
            newModule.setParentModule(parentModule);
            path = parentModule.getPath();
            depth = parentModule.getDepth() + 1;
        } else {
            // This is a root module
            path = "/";
            depth = 0;
        }

        newModule.setDepth(depth);
        // Temporarily set path; will be updated after ID generation
        newModule.setPath("TEMP");

        // First save to generate the ID
        Module savedModule = moduleRepository.save(newModule);

        // Construct the final path with the new ID and update
        String finalPath = path + savedModule.getId().toString() + "/";
        savedModule.setPath(finalPath);

        // Second save to update the path
        return moduleRepository.save(savedModule);
    }

    @Override
    @Transactional(readOnly = true)
    public Module getModuleById(UUID id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Module not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Module> getModuleChildren(UUID moduleId) {
        if (!moduleRepository.existsById(moduleId)) {
            throw new EntityNotFoundException("Module not found with ID: " + moduleId);
        }
        return moduleRepository.findByParentModuleId(moduleId);
    }

    @Override
    public Module updateModule(UUID moduleId, String name, String description) {
        Module moduleToUpdate = getModuleById(moduleId);

        moduleToUpdate.setName(name);
        moduleToUpdate.setDescription(description);

        return moduleRepository.save(moduleToUpdate);
    }

    @Override
    public void deleteModule(UUID id) {
        Module moduleToDelete = getModuleById(id);

        // Find all descendants (including the module itself) using the path
        List<Module> modulesToDelete = moduleRepository.findByPathStartingWith(moduleToDelete.getPath());

        // Future enhancement: Add logic here to handle TestCases within these modules.
        // For example, you might want to delete them, or re-assign them if they can be moved.
        // testCaseRepository.deleteByModuleIn(modulesToDelete);

        moduleRepository.deleteAll(modulesToDelete);
    }
}