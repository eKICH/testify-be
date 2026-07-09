package com.testify.testify.repository;

import com.testify.testify.entity.TestExecution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TestExecutionRepository extends JpaRepository<TestExecution, Long> {
    Page<TestExecution> findByTestRunId(Long runId, Pageable pageable);
}
