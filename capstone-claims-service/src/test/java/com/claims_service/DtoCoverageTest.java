package com.claims_service;

import org.junit.jupiter.api.Test;

import com.claims_service.dto.ClaimDecisionRequest;

import static org.junit.jupiter.api.Assertions.*;

class DtoCoverageTest {

    @Test
    void records_work() {
        ClaimDecisionRequest req = new ClaimDecisionRequest("ok");
        assertEquals("ok", req.remarks());
    }
}
