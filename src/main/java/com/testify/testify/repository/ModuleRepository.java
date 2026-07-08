package com.testify.testify.repository;

import com.testify.testify.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID> {

    /** Finds all direct child modules for a given parent module ID. */
    List<Module> findByParentModuleId(UUID parentModuleId);

    /** Finds a module and all its descendants using the materialized path. */
    List<Module> findByPathStartingWith(String path);

    /** Finds all root modules within a given application. */
    List<Module> findByApplicationIdAndParentModuleIsNull(UUID applicationId);
}