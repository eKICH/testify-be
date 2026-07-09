package com.testify.testify.controller;

import com.testify.testify.dto.ModuleCreateRequest;
import com.testify.testify.dto.ModuleMoveRequest;
import com.testify.testify.dto.ModuleResponse;
import com.testify.testify.security.UserPrincipal;
import com.testify.testify.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping("/api/v1/applications/{appId}/modules")
    public ResponseEntity<ModuleResponse> createModule(@PathVariable Long appId,
                                                         @RequestBody ModuleCreateRequest request,
                                                         @AuthenticationPrincipal UserPrincipal principal) {
        ModuleResponse response = moduleService.createModule(appId, request, principal.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/applications/{appId}/modules")
    public ResponseEntity<List<ModuleResponse>> getModules(@PathVariable Long appId,
                                                             @RequestParam(defaultValue = "false") boolean flat) {
        List<ModuleResponse> response = flat
                ? moduleService.getModulesFlat(appId)
                : moduleService.getModuleTree(appId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/modules/{id}")
    public ResponseEntity<ModuleResponse> getModuleById(@PathVariable Long id) {
        return ResponseEntity.ok(moduleService.getModuleById(id));
    }

    @PutMapping("/api/v1/modules/{id}")
    public ResponseEntity<ModuleResponse> updateModule(@PathVariable Long id,
                                                         @RequestBody ModuleCreateRequest request) {
        return ResponseEntity.ok(moduleService.updateModule(id, request));
    }

    @PatchMapping("/api/v1/modules/{id}/move")
    public ResponseEntity<ModuleResponse> moveModule(@PathVariable Long id,
                                                       @RequestBody ModuleMoveRequest request) {
        return ResponseEntity.ok(moduleService.moveModule(id, request.getNewParentModuleId()));
    }

    @DeleteMapping("/api/v1/modules/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id,
                                              @RequestParam(defaultValue = "false") boolean cascade) {
        moduleService.deleteModule(id, cascade);
        return ResponseEntity.noContent().build();
    }
}
