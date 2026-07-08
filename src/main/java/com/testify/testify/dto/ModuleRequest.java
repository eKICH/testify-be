package com.testify.testify.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ModuleRequest {
    @NotNull(message = "Application ID cannot be null")
    private UUID applicationId;

    @NotBlank(message = "Module name cannot be blank")
    private String name;
    private String description;
    private UUID parentModuleId; // Can be null for root modules
}