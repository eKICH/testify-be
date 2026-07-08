package com.testify.testify.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ModuleResponse {
    private UUID id;
    private String name;
    private String description;
    private UUID applicationId;
    private UUID parentModuleId;
    private String path;
    private int depth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}