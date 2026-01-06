package com.policy_service.Service;

import org.springframework.stereotype.Service;

import com.policy_service.dto.PolicyEnrollRequest;
import com.policy_service.entity.Policy;
import com.policy_service.entity.PolicyStatus;
import com.policy_service.repository.InsurancePlanRepository;
import com.policy_service.repository.PolicyRepository;
import com.policy_service.exception.ResourceNotFoundException;
import com.policy_service.exception.PolicyOperationException;


import java.time.LocalDate;
import java.util.List;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final InsurancePlanRepository planRepository;

    public PolicyService(PolicyRepository policyRepository, InsurancePlanRepository planRepository) {
        this.policyRepository = policyRepository;
        this.planRepository = planRepository;
    }
    
    public Policy getPolicyById(Long policyId) {
        return policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id " + policyId));
    }

    public Policy enroll(PolicyEnrollRequest request) {
        var plan = planRepository.findById(request.planId).orElseThrow();

        Policy policy = new Policy();
        policy.setUserId(request.userId);
        policy.setPlan(plan);
        policy.setStartDate(request.startDate);
        policy.setEndDate(request.startDate.plusYears(plan.getDurationYears()));
        policy.setStatus(PolicyStatus.ACTIVE);

        return policyRepository.save(policy);
    }

    public List<Policy> getUserPolicies(Long userId) {
        return policyRepository.findByUserId(userId);
    }

    public Policy renew(Long policyId) {
    	Policy policy = policyRepository.findById(policyId)
    	        .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id " + policyId));
    	if (policy.getStatus() == PolicyStatus.SUSPENDED) {
    	    throw new PolicyOperationException("Suspended policy cannot be renewed");
    	}
        policy.setEndDate(policy.getEndDate().plusYears(policy.getPlan().getDurationYears()));
        policy.setStatus(PolicyStatus.ACTIVE);
        return policyRepository.save(policy);
    }

    public Policy suspend(Long policyId) {
        Policy policy = policyRepository.findById(policyId).orElseThrow();
        policy.setStatus(PolicyStatus.SUSPENDED);
        return policyRepository.save(policy);
    }
}

