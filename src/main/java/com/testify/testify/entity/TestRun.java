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

@Entity
@Table(name = "test_runs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
