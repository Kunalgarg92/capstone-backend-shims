package com.claims_service.Controller;

import com.claims_service.dto.*;
import com.claims_service.service.ClaimService;

import org.springframework.core.io.Resource;
import jakarta.validation.Valid;
import org.springframework.core.io.*;
import org.springframework.http.*;
import java.nio.file.*;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimService service;

    public ClaimController(ClaimService service) {
        this.service = service;
    }
    
    
    @PostMapping(consumes = { org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE })
    public ClaimResponse submitClaim(
            @ModelAttribute SubmitClaimRequest request, 
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role
    ) {
        System.out.println("Claim received for patient: " + request.getPatientName());
        
        return ClaimResponse.from(
                service.submitClaim(request, userId, role)
        );
    }
    @GetMapping("/user/{userId}")
    public List<ClaimResponse> userClaims(@PathVariable Long userId) {
        return service.getClaimsByUser(userId);
    }
    
    @GetMapping("/documents/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path file = Paths.get("uploads").resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pending")
    public List<ClaimResponse> pendingClaims() {
        return service.getPendingClaims();
    }

    @PutMapping("/{id}/approve")
    public ClaimResponse approve(
            @PathVariable Long id,
            @Valid @RequestBody ClaimDecisionRequest req
    ) {
        return service.approveClaim(id, req);
    }

    @PutMapping("/{id}/reject")
    public ClaimResponse reject(
            @PathVariable Long id,
            @Valid @RequestBody ClaimDecisionRequest req
    ) {
        return service.rejectClaim(id, req);
    }
}
