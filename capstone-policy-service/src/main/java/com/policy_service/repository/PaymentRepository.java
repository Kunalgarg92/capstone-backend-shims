package com.policy_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.policy_service.entity.Payment;
import java.util.*;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
    
    List<Payment> findByPolicyIdAndUserIdOrderByCreatedAtAsc(
            Long policyId,
            Long userId
    );
}

