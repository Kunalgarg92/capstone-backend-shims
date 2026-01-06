package com.claims_service.dto;

import java.util.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubmitClaimRequest {

    @NotNull
    private Long policyId;

    @NotBlank
    private String patientName;

    @NotNull
    private Double treatmentCost;

    private String hospitalName;
    private String hospitalAddress;

    private Long userId;

    private List<MultipartFile> documents;
}

