package com.claims_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.claims_service.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
        boolean existsByIdAndUserId(Long id, Long userId);
    }