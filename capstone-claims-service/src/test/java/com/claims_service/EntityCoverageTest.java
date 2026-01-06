package com.claims_service;

import org.junit.jupiter.api.Test;

import com.claims_service.entity.Claim;
import com.claims_service.entity.ClaimStatus;

import static org.junit.jupiter.api.Assertions.*;

class EntityCoverageTest {

    @Test
    void entity_setters() {
        Claim c = new Claim();
        c.setPatientName("John");
        assertEquals("John", c.getPatientName());
    }

    @Test
    void enum_values() {
        assertEquals(4, ClaimStatus.values().length);
    }
}
