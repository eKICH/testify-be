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
}
