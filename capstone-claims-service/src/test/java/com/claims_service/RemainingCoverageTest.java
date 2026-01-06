package com.claims_service;

import com.claims_service.dto.ClaimCreateRequest;
import com.claims_service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemainingCoverageTest {

    @Test
    void claimCreateRequest_record() {
        ClaimCreateRequest req =
                new ClaimCreateRequest(1L, "John", 100.0, "doc.pdf");

        assertEquals("John", req.patientName());
    }

    @Test
    void resourceNotFoundException() {
        ResourceNotFoundException ex =
                new ResourceNotFoundException("404");

        assertEquals("404", ex.getMessage());
    }

    @Test
    void main_runs() {
        CapstoneClaimsServiceApplication.main(new String[]{});
    }
}
