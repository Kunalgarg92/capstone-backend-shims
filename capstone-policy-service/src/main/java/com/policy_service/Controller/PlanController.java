package com.policy_service.Controller;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.policy_service.Service.PlanService;
import com.policy_service.dto.PlanRequest;
import com.policy_service.entity.InsurancePlan;

import java.util.List;

@RestController
@RequestMapping("/api/admin/plans")
public class PlanController {

    private final PlanService service;

    public PlanController(PlanService service) {
        this.service = service;
    }

    @PostMapping
    public InsurancePlan create(@Valid @RequestBody PlanRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<InsurancePlan> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public InsurancePlan update(@PathVariable Long id, @Valid @RequestBody PlanRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

