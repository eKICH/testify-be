package com.testify.testify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "test_cases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"testSuite", "module", "testPlans"}) // Exclude relationships to prevent loops
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;
    private String preconditions;
    private String steps;
    private String expectedResult;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;

    @Enumerated(EnumType.STRING)
    private TestCaseStatus status = TestCaseStatus.DRAFT;

    @ManyToOne
    @JoinColumn(name = "test_suite_id")
    private TestSuite testSuite;

    // New field for the Application/Module structure
    @ManyToOne
    @JoinColumn(name = "module_id") // Initially nullable for migration
    private Module module;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToMany(mappedBy = "testCases")
    private Set<TestPlan> testPlans = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
