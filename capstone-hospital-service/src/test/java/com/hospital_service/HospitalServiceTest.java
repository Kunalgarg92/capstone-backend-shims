package com.hospital_service;

import com.hospital_service.Feign.ClaimsClient;
import com.hospital_service.dto.ClaimResponse;
import com.hospital_service.dto.HospitalCreateRequest;
import com.hospital_service.entity.Hospital;
import com.hospital_service.repository.HospitalRepository;
import com.hospital_service.service.HospitalService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HospitalServiceTest {

    @Mock
    private HospitalRepository repository;

    @Mock
    private ClaimsClient claimsClient;

    @InjectMocks
    private HospitalService service;

    @Test
    void createHospital_success() {
        HospitalCreateRequest req = new HospitalCreateRequest();
        req.setName("Apollo");

        Hospital saved = new Hospital();
        saved.setId(1L);
        saved.setName("Apollo");

        when(repository.save(any())).thenReturn(saved);

        var response = service.createHospital(req, 1L);

        assertEquals("Apollo", response.getName());
    }

    @Test
    void getAllHospitals_success() {
        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setName("Apollo");

        when(repository.findAll()).thenReturn(List.of(hospital));

        var list = service.getAllHospitals();

        assertEquals(1, list.size());
    }

    @Test
    void getHospitalClaims_success() {
        when(claimsClient.getClaimsByHospital(1L, "HOSPITAL"))
                .thenReturn(List.of(new ClaimResponse()));

        var claims = service.getHospitalClaims(1L, "HOSPITAL");

        assertEquals(1, claims.size());
    }
}
