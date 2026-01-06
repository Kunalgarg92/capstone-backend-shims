package com.hospital_service;

import com.hospital_service.dto.ClaimResponse;
import com.hospital_service.dto.HospitalCreateRequest;
import com.hospital_service.dto.HospitalResponse;
import com.hospital_service.entity.ClaimStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void claimResponse_allArgsConstructorAndGetters() {
        ClaimResponse response = new ClaimResponse(
                1L, 2L, 3L, 4L, "USER",
                5L, "Apollo", true,
                10000.0, ClaimStatus.APPROVED,
                "OK", LocalDateTime.now()
        );

        assertEquals(ClaimStatus.APPROVED, response.getStatus());
    }

    @Test
    void hospitalCreateRequest_settersAndGetters() {
        HospitalCreateRequest req = new HospitalCreateRequest();
        req.setName("Apollo");

        assertEquals("Apollo", req.getName());
    }

    @Test
    void hospitalResponse_fromEntity() {
        var hospital = new com.hospital_service.entity.Hospital();
        hospital.setId(1L);
        hospital.setName("Apollo");

        HospitalResponse response = HospitalResponse.from(hospital);

        assertEquals("Apollo", response.getName());
    }
}
