package com.testify.testify.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationRequest {
    @NotBlank(message = "Application name cannot be blank")
    private String name;
    private String description;
}