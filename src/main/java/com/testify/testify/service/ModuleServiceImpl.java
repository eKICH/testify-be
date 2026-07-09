package com.testify.testify.service;

import com.testify.testify.dto.ModuleCreateRequest;
import com.testify.testify.dto.ModuleResponse;
import com.testify.testify.dto.UserDto;
import com.testify.testify.entity.Application;
import com.testify.testify.entity.Module;
import com.testify.testify.entity.User;
import com.testify.testify.exception.ConflictException;
import com.testify.testify.exception.ResourceNotFoundException;
import com.testify.testify.repository.ApplicationRepository;
import com.testify.testify.repository.ModuleRepository;
import com.testify.testify.repository.TestCaseRepository;
import com.testify.testify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final ApplicationRepository applicationRepository;
    private final TestCaseRepository testCaseRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ModuleResponse createModule(Long applicationId, ModuleCreateRequest request, Long userId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Module parent = null;
        if (request.getParentModuleId() != null) {
            parent = moduleRepository.findById(request.getParentModuleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent module not found with id: " + request.getParentModuleId()));
            if (!parent.getApplication().getId().equals(applicationId)) {
                throw new IllegalArgumentException("Parent module does not belong to this application");
            }
        }

        Module module = new Module();
        module.setName(request.getName());
        module.setDescription(request.getDescription());
        module.setApplication(application);
        module.setParentModule(parent);
        module.setDepth(parent != null ? parent.getDepth() + 1 : 0);
        module.setCreatedBy(user);
        module.setPath(""); // placeholder - id isn't known until after insert (see entity javadoc)

        // First write: IDENTITY generation forces the insert now, giving us the id.
        Module saved = moduleRepository.save(module);

        // Second write: now we can build the real path and persist it.
        String parentPath = parent != null ? parent.getPath() : "/";
        saved.setPath(parentPath + saved.getId() + "/");
        saved = moduleRepository.save(saved);

        return toResponse(saved, false);
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleResponse getModuleById(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + id));
        return toResponse(module, true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuleResponse> getModuleTree(Long applicationId) {
        ensureApplicationExists(applicationId);
        List<Module> topLevel = moduleRepository.findByApplicationIdAndParentModuleIsNullOrderByName(applicationId);
        return topLevel.stream()
                .map(this::buildTreeNode)
                .collect(Collectors.toList());
    }

    private ModuleResponse buildTreeNode(Module module) {
        ModuleResponse node = toResponse(module, false);
        List<Module> children = moduleRepository.findByParentModuleIdOrderByName(module.getId());
        node.setChildren(children.stream().map(this::buildTreeNode).collect(Collectors.toList()));
        return node;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuleResponse> getModulesFlat(Long applicationId) {
        ensureApplicationExists(applicationId);
        return moduleRepository.findByApplicationId(applicationId).stream()
                .sorted(Comparator.comparing(Module::getPath))
                .map(m -> toResponse(m, false))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ModuleResponse updateModule(Long id, ModuleCreateRequest request) {
        // Name/description only - moving to a different parent goes through
        // moveModule(), which handles the path/depth rewrite and cycle check.
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + id));

        module.setName(request.getName());
        module.setDescription(request.getDescription());

        Module saved = moduleRepository.save(module);
        return toResponse(saved, true);
    }

    @Override
    @Transactional
    public ModuleResponse moveModule(Long id, Long newParentModuleId) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + id));

        Module newParent = null;
        if (newParentModuleId != null) {
            if (newParentModuleId.equals(id)) {
                throw new IllegalArgumentException("A module cannot be moved into itself");
            }
            newParent = moduleRepository.findById(newParentModuleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Target parent module not found with id: " + newParentModuleId));

            if (!newParent.getApplication().getId().equals(module.getApplication().getId())) {
                throw new IllegalArgumentException("Cannot move a module to a different application");
            }
            if (newParent.getPath().startsWith(module.getPath())) {
                throw new IllegalArgumentException("Cannot move a module into one of its own descendants");
            }
        }

        String oldPathPrefix = module.getPath();
        int oldDepth = module.getDepth();

        String newParentPath = newParent != null ? newParent.getPath() : "/";
        int newDepth = newParent != null ? newParent.getDepth() + 1 : 0;
        String newPath = newParentPath + module.getId() + "/";
        int depthDelta = newDepth - oldDepth;

        List<Module> subtree = moduleRepository.findSubtree(oldPathPrefix);
        for (Module m : subtree) {
            String suffix = m.getPath().substring(oldPathPrefix.length());
            m.setPath(newPath + suffix);
            m.setDepth(m.getDepth() + depthDelta);
        }
        module.setParentModule(newParent);
        moduleRepository.saveAll(subtree);

        return toResponse(module, true);
    }

    @Override
    @Transactional
    public void deleteModule(Long id, boolean cascade) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + id));

        List<Module> subtree = moduleRepository.findSubtree(module.getPath()); // includes module itself
        boolean hasChildren = subtree.size() > 1;
        long testCaseCount = testCaseRepository.countBySubtreePath(module.getPath());

        if ((hasChildren || testCaseCount > 0) && !cascade) {
            throw new ConflictException(
                    "Module '" + module.getName() + "' has " +
                    (hasChildren ? (subtree.size() - 1) + " sub-module(s)" : "") +
                    (hasChildren && testCaseCount > 0 ? " and " : "") +
                    (testCaseCount > 0 ? testCaseCount + " test case(s)" : "") +
                    ". Move/delete them first, or retry with ?cascade=true.");
        }

        if (testCaseCount > 0) {
            // Preserve test case history - detach rather than delete.
            testCaseRepository.detachFromModules(subtree);
        }

        // Delete deepest modules first so no row still has a live child when removed.
        subtree.sort(Comparator.comparing(Module::getDepth).reversed());
        moduleRepository.deleteAll(subtree);
    }

    private void ensureApplicationExists(Long applicationId) {
        if (!applicationRepository.existsById(applicationId)) {
            throw new ResourceNotFoundException("Application not found with id: " + applicationId);
        }
    }

    private ModuleResponse toResponse(Module module, boolean includeDirectChildren) {
        ModuleResponse response = new ModuleResponse();
        response.setId(module.getId());
        response.setName(module.getName());
        response.setDescription(module.getDescription());
        response.setApplicationId(module.getApplication().getId());
        response.setParentModuleId(module.getParentModule() != null ? module.getParentModule().getId() : null);
        response.setPath(module.getPath());
        response.setDepth(module.getDepth());
        response.setTestCaseCount(testCaseRepository.countBySubtreePath(module.getPath()));

        if (module.getCreatedBy() != null) {
            User creator = module.getCreatedBy();
            response.setCreatedBy(new UserDto(creator.getId(), creator.getUsername(), creator.getFirstName(), creator.getLastName()));
        }

        response.setCreatedAt(module.getCreatedAt());
        response.setUpdatedAt(module.getUpdatedAt());

        if (includeDirectChildren) {
            List<Module> children = moduleRepository.findByParentModuleIdOrderByName(module.getId());
            response.setChildren(children.stream().map(c -> toResponse(c, false)).collect(Collectors.toList()));
        }

        return response;
    }
}
