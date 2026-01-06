package com.hospital_service.dto;

import java.time.LocalDateTime;

import com.hospital_service.entity.ClaimStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimResponse {

    private Long id;

    private Long policyId;
    private Long userId;

    private Long submittedById;
    private String submittedByRole;

    private Long hospitalId;
    private String hospitalName;
    private Boolean inNetwork;

    private Double treatmentCost;

    private ClaimStatus status;
    private String remarks;

    private LocalDateTime createdAt;
}

