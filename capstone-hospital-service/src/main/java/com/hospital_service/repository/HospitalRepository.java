package com.hospital_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital_service.entity.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    boolean existsByEmail(String email);
}

