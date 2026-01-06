package com.claims_service.dto;

import com.claims_service.entity.Claim;
import com.claims_service.entity.ClaimStatus;
import java.util.*;
import java.time.LocalDateTime;

public record ClaimResponse(
        Long id,
        Long policyId,

        Long submittedById,
        String submittedByRole,

        Long hospitalId,
        String hospitalName,
        Boolean inNetwork,

        Double treatmentCost,
        ClaimStatus status,
        String remarks,
        LocalDateTime createdAt,
        List<String> documents 
) {
    public static ClaimResponse from(Claim claim) {
        return new ClaimResponse(
                claim.getId(),
                claim.getPolicyId(),

                claim.getSubmittedById(),
                claim.getSubmittedByRole(),

                claim.getHospitalId(),
                claim.getHospitalName(),
                claim.getInNetwork(),

                claim.getTreatmentCost(),
                claim.getStatus(),
                claim.getRemarks(),
                claim.getCreatedAt(),
                claim.getDocuments()
        );
    }
}
