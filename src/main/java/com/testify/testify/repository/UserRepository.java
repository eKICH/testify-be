package com.testify.testify.repository;

import com.testify.testify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT count(u) FROM User u WHERE u.createdAt >= :startOfDay")
    long countByCreatedAtAfter(LocalDateTime startOfDay);
}
