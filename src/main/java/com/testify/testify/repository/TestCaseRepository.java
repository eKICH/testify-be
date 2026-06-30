package com.testify.testify.repository;

import com.testify.testify.entity.TestCase;
import com.testify.testify.entity.TestPlan;
import com.testify.testify.entity.TestSuite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    Page<TestCase> findByTestSuite(TestSuite testSuite, Pageable pageable);

    Page<TestCase> findByTestPlans(TestPlan testPlan, Pageable pageable);
}
