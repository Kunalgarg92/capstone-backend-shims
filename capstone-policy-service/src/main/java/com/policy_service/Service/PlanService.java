package com.policy_service.Service;

import org.springframework.stereotype.Service;

import com.policy_service.dto.PlanRequest;
import com.policy_service.entity.InsurancePlan;
import com.policy_service.repository.InsurancePlanRepository;
import com.policy_service.exception.ResourceNotFoundException;


import java.util.List;

@Service
public class PlanService {

    private final InsurancePlanRepository repository;

    public PlanService(InsurancePlanRepository repository) {
        this.repository = repository;
    }

    public InsurancePlan create(PlanRequest request) {
        InsurancePlan plan = new InsurancePlan();
        plan.setName(request.name);
        plan.setCoverageLimit(request.coverageLimit);
        plan.setPremiumAmount(request.premiumAmount);
        plan.setDurationYears(request.durationYears);
        return repository.save(plan);
    }

    public List<InsurancePlan> getAll() {
        return repository.findAll();
    }

    public InsurancePlan update(Long id, PlanRequest request) {
        InsurancePlan plan = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Insurance plan not found with id " + id
                ));

        plan.setName(request.name);
        plan.setCoverageLimit(request.coverageLimit);
        plan.setPremiumAmount(request.premiumAmount);
        plan.setDurationYears(request.durationYears);

        return repository.save(plan);
    }


    public void delete(Long id) {
        repository.deleteById(id);
    }
}

