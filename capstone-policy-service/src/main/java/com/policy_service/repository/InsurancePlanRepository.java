package com.policy_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.policy_service.entity.InsurancePlan;

public interface InsurancePlanRepository extends JpaRepository<InsurancePlan, Long> {
}

