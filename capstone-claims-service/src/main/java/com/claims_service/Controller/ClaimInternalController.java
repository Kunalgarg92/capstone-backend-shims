package com.claims_service.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claims_service.dto.ClaimResponse;
import com.claims_service.exception.BusinessException;
import com.claims_service.service.ClaimService;

@RestController
@RequestMapping("/api/internal/claims")
public class ClaimInternalController {

    private final ClaimService claimService;

    public ClaimInternalController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping("/hospital/claims")
    public List<ClaimResponse> getHospitalClaims(
            @RequestHeader("X-User-Id") Long hospitalId,
            @RequestHeader("X-User-Role") String role
    ) {
        if (!"HOSPITAL".equals(role)) {
            throw new BusinessException("Only hospitals can access this endpoint");
        }
        return claimService.getClaimsByHospital(hospitalId);
    }

}
