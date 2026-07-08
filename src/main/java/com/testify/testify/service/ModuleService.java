package com.testify.testify.service;

import com.testify.testify.entity.Module;
import com.testify.testify.dto.ModuleRequest;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing Module entities and their hierarchical structure.
 */
public interface ModuleService {

    /**
     * Creates a new module from a request object.
     * @param request The request containing module details.
     * @return The newly created Module entity.
     */
    Module createModule(ModuleRequest request);

    Module getModuleById(UUID id);

    List<Module> getModuleChildren(UUID moduleId);

    Module updateModule(UUID moduleId, String name, String description);

    void deleteModule(UUID id);
}