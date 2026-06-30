package com.testify.testify.dto;

import com.testify.testify.entity.Severity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugsBySeverityDto {
    private Map<Severity, Long> bugsBySeverity;
}
