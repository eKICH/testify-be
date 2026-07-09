package com.testify.testify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleResponse {
    private Long id;
    private String name;
    private String description;
    private Long applicationId;
    private Long parentModuleId;
    private String path;
    private Integer depth;

    // Count includes this module's own test cases plus every descendant's.
    private long testCaseCount;

    // Populated for tree responses (?flat=false, the default); left empty for flat responses.
    private List<ModuleResponse> children = new ArrayList<>();

    private UserDto createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
