package com.testify.testify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleMoveRequest {
    // Null means "move to top-level" (no parent).
    private Long newParentModuleId;

    public Long getNewParentModuleId() { return newParentModuleId; }
}
