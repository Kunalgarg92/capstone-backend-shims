package com.hospital_service.Feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.hospital_service.dto.ClaimResponse;

@FeignClient(name = "claims-service")
public interface ClaimsClient {

    @GetMapping("/api/internal/claims/hospital/claims")
    List<ClaimResponse> getClaimsByHospital(
            @RequestHeader("X-User-Id") Long hospitalId,
            @RequestHeader("X-User-Role") String role
    );
}

