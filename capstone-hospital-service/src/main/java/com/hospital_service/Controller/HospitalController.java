package com.hospital_service.Controller;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hospital_service.dto.ClaimResponse;
import com.hospital_service.dto.HospitalCreateRequest;
import com.hospital_service.dto.HospitalResponse;
import com.hospital_service.service.HospitalService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    private final HospitalService service;

    public HospitalController(HospitalService service) {
        this.service = service;
    }

    @PostMapping
    public HospitalResponse createHospital(
            @RequestBody @Valid HospitalCreateRequest request,
            @RequestHeader("X-User-Id") Long hospitalId,
            @RequestHeader("X-User-Role") String role
    ) {
        if (!"HOSPITAL".equals(role)) {
            throw new RuntimeException("Only hospital users can create hospital profile");
        }
        return service.createHospital(request, hospitalId);
    }

    @GetMapping
    public List<HospitalResponse> getHospitals() {
        return service.getAllHospitals();
    }

    @GetMapping("/claims")
    public List<ClaimResponse> hospitalClaims(
            @RequestHeader("X-User-Id") Long hospitalId,
            @RequestHeader("X-User-Role") String role
    ) {
        if (!"HOSPITAL".equals(role)) {
            throw new RuntimeException("Only hospitals can view claims");
        }
        return service.getHospitalClaims(hospitalId, role);
    }
}
