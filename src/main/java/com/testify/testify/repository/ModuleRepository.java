package com.testify.testify.repository;

import com.testify.testify.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findByApplicationIdAndParentModuleIsNullOrderByName(Long applicationId);

    List<Module> findByApplicationId(Long applicationId);

    List<Module> findByParentModuleIdOrderByName(Long parentModuleId);

    boolean existsByParentModuleId(Long parentModuleId);

    // "Subtree rooted at this module" - path LIKE '<thisPath>%' matches the
    // module itself (its own path is a prefix of itself) plus every descendant,
    // since a descendant's path always starts with its ancestor's full path.
    @Query("SELECT m FROM Module m WHERE m.path LIKE CONCAT(:pathPrefix, '%')")
    List<Module> findSubtree(@Param("pathPrefix") String pathPrefix);

    boolean existsByApplicationId(Long applicationId);
}
