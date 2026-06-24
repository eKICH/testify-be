package com.testify.testify.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRunCreateRequest {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Long testPlanId;

    private String buildVersion;
    private String environment;
}
