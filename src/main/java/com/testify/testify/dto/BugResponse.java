package com.testify.testify.dto;

import com.testify.testify.entity.BugStatus;
import com.testify.testify.entity.Priority;
import com.testify.testify.entity.Severity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugResponse {
    private Long id;
    private String title;
    private String description;
    private Severity severity;
    private Priority priority;
    private BugStatus status;
    private String environment;
    private UserDto assignedTo;
    private UserDto reportedBy;
    private LocalDate targetFixDate;
    private LocalDate resolvedDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
