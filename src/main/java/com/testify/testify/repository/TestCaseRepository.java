package com.testify.testify.repository;

import com.testify.testify.entity.Module;
import com.testify.testify.entity.TestCase;
import com.testify.testify.entity.TestPlan;
import com.testify.testify.entity.TestSuite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    Page<TestCase> findByTestSuite(TestSuite testSuite, Pageable pageable);

    Page<TestCase> findByTestPlans(TestPlan testPlan, Pageable pageable);

    Page<TestCase> findByModule(Module module, Pageable pageable);

    boolean existsByModule(Module module);

    // Count/list test cases across a module and all of its descendants,
    // using the same path-prefix trick as ModuleRepository#findSubtree.
    @Query("SELECT COUNT(tc) FROM TestCase tc WHERE tc.module.path LIKE CONCAT(:pathPrefix, '%')")
    long countBySubtreePath(@Param("pathPrefix") String pathPrefix);

    @Query("SELECT tc FROM TestCase tc WHERE tc.module.path LIKE CONCAT(:pathPrefix, '%')")
    Page<TestCase> findBySubtreePath(@Param("pathPrefix") String pathPrefix, Pageable pageable);

    // Used by cascade module deletion: detach (don't delete) test cases whose
    // module is being removed, so test case history survives a folder cleanup.
    @Modifying
    @Query("UPDATE TestCase tc SET tc.module = NULL WHERE tc.module IN :modules")
    void detachFromModules(@Param("modules") List<Module> modules);
}
