package com.policy_service.Controller;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.policy_service.Service.PolicyService;
import com.policy_service.dto.PolicyEnrollRequest;
import com.policy_service.entity.Policy;
import java.util.List;
@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService service;

    public PolicyController(PolicyService service) {
        this.service = service;
    }
    
    @GetMapping("/{id}")
    public Policy getPolicyById(@PathVariable Long id) {
        return service.getPolicyById(id);
    }

    @PostMapping("/enroll")
    public Policy enroll(@Valid @RequestBody PolicyEnrollRequest request) {
        return service.enroll(request);
    }

    @GetMapping("/user/{userId}")
    public List<Policy> getUserPolicies(@PathVariable Long userId) {
        return service.getUserPolicies(userId);
    }

    @PutMapping("/{id}/renew")
    public Policy renew(@PathVariable Long id) {
        return service.renew(id);
    }

    @PutMapping("/{id}/suspend")
    public Policy suspend(@PathVariable Long id) {
        return service.suspend(id);
    }
    
}



