package com.testify.testify.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestPlanCreateRequest {
    @NotBlank
    private String name;

    private String description;
    private String scope;
    private LocalDate startDate;
    private LocalDate endDate;

    // Explicit Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getScope() { return scope; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
}
