package com.testify.testify.service;

import com.testify.testify.dto.ModuleCreateRequest;
import com.testify.testify.dto.ModuleResponse;

import java.util.List;

public interface ModuleService {
    ModuleResponse createModule(Long applicationId, ModuleCreateRequest request, Long userId);
    ModuleResponse getModuleById(Long id);

    /** Top-level tree (children nested) for an application. */
    List<ModuleResponse> getModuleTree(Long applicationId);

    /** Flat list (no children populated) for an application - cheaper for dropdowns/breadcrumbs. */
    List<ModuleResponse> getModulesFlat(Long applicationId);

    ModuleResponse updateModule(Long id, ModuleCreateRequest request);
    ModuleResponse moveModule(Long id, Long newParentModuleId);

    /** cascade=true deletes the whole subtree and orphans (does not delete) any test cases in it. */
    void deleteModule(Long id, boolean cascade);
}
