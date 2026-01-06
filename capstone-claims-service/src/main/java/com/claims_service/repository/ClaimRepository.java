package com.claims_service.repository;

import com.claims_service.entity.Claim;
import com.claims_service.entity.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

    List<Claim> findByUserId(Long userId);

    List<Claim> findBySubmittedById(Long submittedById);

    List<Claim> findByStatus(ClaimStatus status);
    
    List<Claim> findByHospitalId(Long hospitalId);
}
