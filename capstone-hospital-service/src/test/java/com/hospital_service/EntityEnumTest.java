package com.hospital_service;

import org.junit.jupiter.api.Test;

import com.hospital_service.entity.ClaimStatus;
import com.hospital_service.entity.Hospital;

import static org.junit.jupiter.api.Assertions.*;

class EntityEnumTest {

    @Test
    void hospital_entity_gettersSetters() {
        Hospital hospital = new Hospital();
        hospital.setName("Apollo");

        assertEquals("Apollo", hospital.getName());
    }

    @Test
    void claimStatus_enum_values() {
        assertEquals(3, ClaimStatus.values().length);
        assertEquals(ClaimStatus.SUBMITTED, ClaimStatus.valueOf("SUBMITTED"));
    }
}
