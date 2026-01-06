package com.claims_service;

import com.claims_service.Feign.PolicyClient;
import com.claims_service.dto.*;
import com.claims_service.entity.*;
import com.claims_service.exception.BusinessException;
import com.claims_service.repository.ClaimRepository;
import com.claims_service.service.ClaimService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClaimServiceTest {

    @Mock
    private ClaimRepository repository;

    @Mock
    private PolicyClient policyClient;

    @InjectMocks
    private ClaimService service;

    @Test
    void submitClaim_customer_success() {
        SubmitClaimRequest req = new SubmitClaimRequest();
        req.setPolicyId(1L);
        req.setPatientName("John");
        req.setTreatmentCost(100.0);

        when(policyClient.validatePolicy(1L, 1L))
                .thenReturn(new PolicyValidationResponse(true, 1L, 1L, "ACTIVE"));

        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        Claim claim = service.submitClaim(req, 1L, "CUSTOMER");

        assertEquals(ClaimStatus.SUBMITTED, claim.getStatus());
    }

    @Test
    void submitClaim_hospital_missingUserId() {
        SubmitClaimRequest req = new SubmitClaimRequest();
        req.setPolicyId(1L);

        assertThrows(BusinessException.class,
                () -> service.submitClaim(req, 1L, "HOSPITAL"));
    }

    @Test
    void submitClaim_invalidRole() {
        SubmitClaimRequest req = new SubmitClaimRequest();

        assertThrows(BusinessException.class,
                () -> service.submitClaim(req, 1L, "ADMIN"));
    }

    @Test
    void getClaimsByUser() {
        when(repository.findByUserId(1L)).thenReturn(List.of(new Claim()));
        assertEquals(1, service.getClaimsByUser(1L).size());
    }

    @Test
    void getPendingClaims() {
        when(repository.findByStatus(ClaimStatus.SUBMITTED))
                .thenReturn(List.of(new Claim()));
        assertEquals(1, service.getPendingClaims().size());
    }

    @Test
    void approveClaim_success() {
        Claim claim = new Claim();
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(claim));
        when(repository.save(any())).thenReturn(claim);

        ClaimResponse res = service.approveClaim(1L, new ClaimDecisionRequest("ok"));
        assertEquals(ClaimStatus.APPROVED, res.status());
    }

    @Test
    void rejectClaim_success() {
        Claim claim = new Claim();
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(claim));
        when(repository.save(any())).thenReturn(claim);

        ClaimResponse res = service.rejectClaim(1L, new ClaimDecisionRequest("no"));
        assertEquals(ClaimStatus.REJECTED, res.status());
    }

    @Test
    void getClaimsByHospital() {
        when(repository.findByHospitalId(1L))
                .thenReturn(List.of(new Claim()));
        assertEquals(1, service.getClaimsByHospital(1L).size());
    }
//    @Test
//    void submitClaim_fileUploadFailure_throwsBusinessException() throws Exception {
//        SubmitClaimRequest req = new SubmitClaimRequest();
//        req.setPolicyId(1L);
//        req.setPatientName("John");
//        req.setTreatmentCost(500.0);
//
//        MultipartFile badFile = mock(MultipartFile.class);
//        when(badFile.getOriginalFilename()).thenReturn("bad.pdf");
//        when(badFile.getInputStream()).thenThrow(new IOException("disk error"));
//
//        req.setDocuments(List.of(badFile));
//
//        when(policyClient.validatePolicy(1L, 1L))
//                .thenReturn(new PolicyValidationResponse(true, 1L, 1L, "ACTIVE"));
//
//        assertThrows(BusinessException.class,
//                () -> service.submitClaim(req, 1L, "CUSTOMER"));
//    }

    @Test
    void submitClaim_hospital_policyInvalid_selectedUser() {
        SubmitClaimRequest req = new SubmitClaimRequest();
        req.setPolicyId(1L);
        req.setPatientName("John");
        req.setTreatmentCost(200.0);
        req.setUserId(99L); // selected user

        when(policyClient.validatePolicy(1L, 99L))
                .thenReturn(new PolicyValidationResponse(false, 1L, 99L, "EXPIRED"));

        assertThrows(BusinessException.class,
                () -> service.submitClaim(req, 10L, "HOSPITAL"));
    }
    
    @Test
    void submitClaim_customer_policyInvalid_loggedInUser() {
        SubmitClaimRequest req = new SubmitClaimRequest();
        req.setPolicyId(1L);
        req.setPatientName("John");
        req.setTreatmentCost(100.0);

        when(policyClient.validatePolicy(1L, 1L))
                .thenReturn(new PolicyValidationResponse(false, 1L, 1L, "EXPIRED"));

        assertThrows(BusinessException.class,
                () -> service.submitClaim(req, 1L, "CUSTOMER"));
    }

    
    @Test
    void submitClaim_withDocuments_success() {
        SubmitClaimRequest req = new SubmitClaimRequest();
        req.setPolicyId(1L);
        req.setPatientName("John");
        req.setTreatmentCost(300.0);

        MockMultipartFile file = new MockMultipartFile(
                "documents",
                "report.pdf",
                "application/pdf",
                "data".getBytes()
        );

        req.setDocuments(List.of(file));

        when(policyClient.validatePolicy(1L, 1L))
                .thenReturn(new PolicyValidationResponse(true, 1L, 1L, "ACTIVE"));

        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        Claim claim = service.submitClaim(req, 1L, "CUSTOMER");

        assertNotNull(claim.getDocuments());
        assertEquals(1, claim.getDocuments().size());
    }


}
