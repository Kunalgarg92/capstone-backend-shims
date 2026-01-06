package com.policy_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.policy_service.entity.Policy;
import java.util.Optional;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> findByUserId(Long userId);
    Optional<Policy> findByIdAndUserId(Long id, Long userId);
    boolean existsByIdAndUserId(Long id, Long userId);
}

