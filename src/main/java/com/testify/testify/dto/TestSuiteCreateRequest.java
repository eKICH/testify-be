package com.testify.testify.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestSuiteCreateRequest {
    @NotBlank
    private String name;

    private String description;

    // Explicit Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
