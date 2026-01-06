package com.hospital_service;

import com.hospital_service.entity.Hospital;
import com.hospital_service.repository.HospitalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class HospitalRepositoryTest {

    @Autowired
    private HospitalRepository repository;

    @Test
    void existsByEmail_worksCorrectly() {
        Hospital hospital = new Hospital();

        // ✅ MANUAL ID ASSIGNMENT (REQUIRED)
        hospital.setId(1L);

        hospital.setName("Apollo");
        hospital.setEmail("test@hospital.com");
        hospital.setPhone("9999999999");
        hospital.setAddress("Hyd");
        hospital.setLicenseNumber("LIC123");
        hospital.setInNetwork(true);

        repository.save(hospital);  // ✅ NOW SAFE

        assertTrue(repository.existsByEmail("test@hospital.com"));
    }
}
