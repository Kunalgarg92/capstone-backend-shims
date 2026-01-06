package com.hospital_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital_service.Feign.ClaimsClient;
import com.hospital_service.dto.ClaimResponse;
import com.hospital_service.dto.HospitalCreateRequest;
import com.hospital_service.dto.HospitalResponse;
import com.hospital_service.entity.Hospital;
import com.hospital_service.repository.HospitalRepository;

@Service
public class HospitalService {

    private final HospitalRepository repository;
    private final ClaimsClient claimsClient;

    public HospitalService(
            HospitalRepository repository,
            ClaimsClient claimsClient
    ) {
        this.repository = repository;
        this.claimsClient = claimsClient;
    }

    public HospitalResponse createHospital(
            HospitalCreateRequest request,
            Long hospitalId
    ) {
        Hospital hospital = new Hospital();
        hospital.setId(hospitalId); 
        hospital.setName(request.getName());
        hospital.setEmail(request.getEmail());
        hospital.setPhone(request.getPhone());
        hospital.setAddress(request.getAddress());
        hospital.setLicenseNumber(request.getLicenseNumber());
        hospital.setInNetwork(request.getInNetwork());

        return HospitalResponse.from(repository.save(hospital));
    }

    public List<HospitalResponse> getAllHospitals() {
        return repository.findAll()
                .stream()
                .map(HospitalResponse::from)
                .toList();
    }

    public List<ClaimResponse> getHospitalClaims(Long hospitalId, String role) {
        return claimsClient.getClaimsByHospital(hospitalId, role);
    }
}


