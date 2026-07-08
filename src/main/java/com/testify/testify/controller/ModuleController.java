package com.testify.testify.controller;

import com.testify.testify.dto.ModuleRequest;
import com.testify.testify.dto.ModuleResponse;
import jakarta.validation.Valid;
import com.testify.testify.entity.Module;
import com.testify.testify.mapper.DtoMapper;
import com.testify.testify.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;
    private final DtoMapper mapper;

    @PostMapping
    public ResponseEntity<ModuleResponse> createModule(@Valid @RequestBody ModuleRequest request) {
        Module module = moduleService.createModule(request);
        return new ResponseEntity<>(mapper.toResponse(module), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleResponse> getModuleById(@PathVariable UUID id) {
        Module module = moduleService.getModuleById(id);
        return ResponseEntity.ok(mapper.toResponse(module));
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<List<ModuleResponse>> getModuleChildren(@PathVariable UUID id) {
        List<ModuleResponse> children = moduleService.getModuleChildren(id).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(children);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleResponse> updateModule(@PathVariable UUID id, @Valid @RequestBody ModuleRequest request) {
        // Note: The request's parentModuleId is ignored to prevent re-parenting via this endpoint.
        Module updatedModule = moduleService.updateModule(id, request.getName(), request.getDescription());
        return ResponseEntity.ok(mapper.toResponse(updatedModule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable UUID id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }
}