package com.claims_service.service;

import com.claims_service.Feign.PolicyClient;
import com.claims_service.dto.*;
import com.claims_service.entity.*;
import com.claims_service.exception.*;

import com.claims_service.repository.ClaimRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ClaimService {

    private final ClaimRepository repository;
    private final PolicyClient policyClient;
    private final Path root = Paths.get("uploads");
    public ClaimService(ClaimRepository repository, PolicyClient policyClient) {
        this.repository = repository;
        this.policyClient = policyClient;
        try {
            if (!Files.exists(root)) Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    
    public Claim submitClaim(
            SubmitClaimRequest req,
            Long loggedInUserId,
            String role
    ) {
        Claim claim = new Claim();

        claim.setPolicyId(req.getPolicyId());
        claim.setPatientName(req.getPatientName());
        claim.setTreatmentCost(req.getTreatmentCost());
        claim.setCreatedAt(LocalDateTime.now());
        claim.setStatus(ClaimStatus.SUBMITTED);

        claim.setSubmittedById(loggedInUserId);
        claim.setSubmittedByRole(role);

        // 🏥 HOSPITAL submits claim
        if ("HOSPITAL".equals(role)) {

            if (req.getUserId() == null) {
                throw new BusinessException("User ID is required for hospital claim");
            }

            PolicyValidationResponse validation =
                    policyClient.validatePolicy(
                            req.getPolicyId(),
                            req.getUserId()
                    );

            if (!validation.valid()) {
                throw new BusinessException("Policy does not belong to selected user");
            }

            // 🔥 IMPORTANT PART
            claim.setUserId(req.getUserId());       
            claim.setHospitalId(loggedInUserId);    
            claim.setHospitalName("EMPANELLED_HOSPITAL");
            claim.setInNetwork(true);
        }

        // 👤 CUSTOMER submits claim
        else if ("CUSTOMER".equals(role)) {

            PolicyValidationResponse validation =
                    policyClient.validatePolicy(
                            req.getPolicyId(),
                            loggedInUserId
                    );

            if (!validation.valid()) {
                throw new BusinessException("Policy does not belong to logged-in user");
            }

            claim.setUserId(loggedInUserId);
            claim.setHospitalName(req.getHospitalName());
            claim.setHospitalAddress(req.getHospitalAddress());
            claim.setInNetwork(false);
        }

        else {
            throw new BusinessException("Invalid role for claim submission");
        }
        System.out.println("Documents received: " + (req.getDocuments() != null ? req.getDocuments().size() : 0));
        
        if (req.getDocuments() != null && !req.getDocuments().isEmpty()) {
            List<String> storedFilenames = new ArrayList<>();
            for (MultipartFile file : req.getDocuments()) {
                try {
                    String uniqueName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Files.copy(file.getInputStream(), this.root.resolve(uniqueName));
                    storedFilenames.add(uniqueName);
                } catch (Exception e) {
                    throw new BusinessException("File upload failed: " + e.getMessage());
                }
            }
            claim.setDocuments(storedFilenames);
        }

        return repository.save(claim);
    }
    
    



    public List<ClaimResponse> getClaimsByUser(Long userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(ClaimResponse::from)
                .toList();
    }

    public List<ClaimResponse> getPendingClaims() {
        return repository.findByStatus(ClaimStatus.SUBMITTED)
                .stream()
                .map(ClaimResponse::from)
                .toList();
    }

    public ClaimResponse approveClaim(Long id, ClaimDecisionRequest req) {
        Claim claim = repository.findById(id)
                .orElseThrow();

        claim.setStatus(ClaimStatus.APPROVED);
        claim.setRemarks(req.remarks());

        return ClaimResponse.from(repository.save(claim));
    }
    
    public List<ClaimResponse> getClaimsByHospital(Long hospitalId) {
        return repository.findByHospitalId(hospitalId)
                .stream()
                .map(ClaimResponse::from)
                .toList();
    }


    public ClaimResponse rejectClaim(Long id, ClaimDecisionRequest req) {
        Claim claim = repository.findById(id)
                .orElseThrow();

        claim.setStatus(ClaimStatus.REJECTED);
        claim.setRemarks(req.remarks());

        return ClaimResponse.from(repository.save(claim));
    }
}

