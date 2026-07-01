package com.testify.testify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "test_executions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "test_run_id", nullable = false)
    private TestRun testRun;

    @ManyToOne
    @JoinColumn(name = "test_case_id", nullable = false)
    private TestCase testCase;

    @Enumerated(EnumType.STRING)
    private ExecutionStatus status = ExecutionStatus.NOT_EXECUTED;

    private String actualResult;
    private String comments;

    @ManyToOne
    @JoinColumn(name = "executed_by")
    private User executedBy;

    private LocalDateTime executedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Explicit Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public TestRun getTestRun() { return testRun; }
    public void setTestRun(TestRun testRun) { this.testRun = testRun; }
    public TestCase getTestCase() { return testCase; }
    public void setTestCase(TestCase testCase) { this.testCase = testCase; }
    public ExecutionStatus getStatus() { return status; }
    public void setStatus(ExecutionStatus status) { this.status = status; }
    public String getActualResult() { return actualResult; }
    public void setActualResult(String actualResult) { this.actualResult = actualResult; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    public User getExecutedBy() { return executedBy; }
    public void setExecutedBy(User executedBy) { this.executedBy = executedBy; }
    public LocalDateTime getExecutedAt() { return executedAt; }
    public void setExecutedAt(LocalDateTime executedAt) { this.executedAt = executedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
