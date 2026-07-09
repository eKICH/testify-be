package com.testify.testify.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleCreateRequest {
    @NotBlank
    private String name;

    private String description;

    // Omit/null for a top-level module.
    private Long parentModuleId;

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Long getParentModuleId() { return parentModuleId; }
}
