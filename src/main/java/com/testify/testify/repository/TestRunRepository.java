package com.testify.testify.repository;

import com.testify.testify.entity.TestRun;
import com.testify.testify.entity.TestRunStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TestRunRepository extends JpaRepository<TestRun, Long> {
    long countByStatus(TestRunStatus status);

    @Query("SELECT new map(t.status as status, count(t) as count) FROM TestRun t GROUP BY t.status")
    List<Map<String, Object>> countByStatus();

    Page<TestRun> findByTestPlanId(Long planId, Pageable pageable);
}
