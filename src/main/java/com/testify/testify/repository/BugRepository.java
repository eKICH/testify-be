package com.testify.testify.repository;

import com.testify.testify.entity.Bug;
import com.testify.testify.entity.BugStatus;
import com.testify.testify.entity.TestCase;
import com.testify.testify.entity.TestRun;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {
    long countByStatus(BugStatus status);

    @Query("SELECT new map(b.severity as severity, count(b) as count) FROM Bug b GROUP BY b.severity")
    List<Map<String, Object>> countBySeverity();

    Page<Bug> findByTestRun(TestRun testRun, Pageable pageable);

    Page<Bug> findByTestCase(TestCase testCase, Pageable pageable);
}
