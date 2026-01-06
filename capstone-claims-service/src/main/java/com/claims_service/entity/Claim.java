package com.claims_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long policyId;
    private Long userId;

    private Long submittedById;
    private String submittedByRole;

    private Long hospitalId;
    private String hospitalName;
    private String hospitalAddress;
    private Boolean inNetwork;
    private String patientName;     
    private Double treatmentCost;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    private String remarks;

    private LocalDateTime createdAt;
    
    @ElementCollection
    @CollectionTable(
        name = "claim_documents", 
        joinColumns = @JoinColumn(name = "claim_id") 
    )
    @Column(name = "file_name")
    private List<String> documents = new ArrayList<>();
}
