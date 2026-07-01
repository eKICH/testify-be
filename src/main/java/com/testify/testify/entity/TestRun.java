package com.testify.testify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "test_runs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;
    private String buildVersion;
    private String environment;

    @Enumerated(EnumType.STRING)
    private TestRunStatus status = TestRunStatus.NOT_STARTED;

    private LocalDateTime executionStartDate;
    private LocalDateTime executionEndDate;

    private Integer totalTestCount = 0;
    private Integer passedCount = 0;
    private Integer failedCount = 0;
    private Integer blockedCount = 0;

    @ManyToOne
    @JoinColumn(name = "test_plan_id", nullable = false)
    private TestPlan testPlan;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "testRun", cascade = CascadeType.ALL)
    private Set<TestExecution> testExecutions = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Explicit Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getBuildVersion() { return buildVersion; }
    public void setBuildVersion(String buildVersion) { this.buildVersion = buildVersion; }
    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }
    public TestRunStatus getStatus() { return status; }
    public void setStatus(TestRunStatus status) { this.status = status; }
    public LocalDateTime getExecutionStartDate() { return executionStartDate; }
    public void setExecutionStartDate(LocalDateTime executionStartDate) { this.executionStartDate = executionStartDate; }
    public LocalDateTime getExecutionEndDate() { return executionEndDate; }
    public void setExecutionEndDate(LocalDateTime executionEndDate) { this.executionEndDate = executionEndDate; }
    public Integer getTotalTestCount() { return totalTestCount; }
    public void setTotalTestCount(Integer totalTestCount) { this.totalTestCount = totalTestCount; }
    public Integer getPassedCount() { return passedCount; }
    public void setPassedCount(Integer passedCount) { this.passedCount = passedCount; }
    public Integer getFailedCount() { return failedCount; }
    public void setFailedCount(Integer failedCount) { this.failedCount = failedCount; }
    public Integer getBlockedCount() { return blockedCount; }
    public void setBlockedCount(Integer blockedCount) { this.blockedCount = blockedCount; }
    public TestPlan getTestPlan() { return testPlan; }
    public void setTestPlan(TestPlan testPlan) { this.testPlan = testPlan; }
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public Set<TestExecution> getTestExecutions() { return testExecutions; }
    public void setTestExecutions(Set<TestExecution> testExecutions) { this.testExecutions = testExecutions; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
