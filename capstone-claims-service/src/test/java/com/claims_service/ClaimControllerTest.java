package com.claims_service;

import com.claims_service.Controller.ClaimController;
import com.claims_service.dto.*;
import com.claims_service.service.ClaimService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClaimController.class)
class ClaimControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClaimService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void submitClaim_success() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("documents", "test.pdf",
                        "application/pdf", "data".getBytes());

        Mockito.when(service.submitClaim(Mockito.any(), Mockito.eq(1L), Mockito.eq("CUSTOMER")))
                .thenReturn(new com.claims_service.entity.Claim());

        mockMvc.perform(multipart("/api/claims")
                        .file(file)
                        .param("policyId", "1")
                        .param("patientName", "John")
                        .param("treatmentCost", "1000")
                        .header("X-User-Id", 1L)
                        .header("X-User-Role", "CUSTOMER"))
                .andExpect(status().isOk());
    }

    @Test
    void userClaims_success() throws Exception {
        Mockito.when(service.getClaimsByUser(1L))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/claims/user/1"))
                .andExpect(status().isOk());
    }
    
    @Test
    void getFile_notFound() {
        assertThrows(
                java.io.FileNotFoundException.class,
                () -> mockMvc.perform(
                        get("/api/claims/documents/nonexistent.pdf")
                ).andReturn()
        );
    }




    @Test
    void pendingClaims_success() throws Exception {
        Mockito.when(service.getPendingClaims()).thenReturn(List.of());

        mockMvc.perform(get("/api/claims/pending"))
                .andExpect(status().isOk());
    }

    @Test
    void approve_success() throws Exception {
        Mockito.when(service.approveClaim(Mockito.eq(1L), Mockito.any()))
                .thenReturn(null);

        mockMvc.perform(put("/api/claims/1/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"remarks\":\"ok\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void reject_success() throws Exception {
        Mockito.when(service.rejectClaim(Mockito.eq(1L), Mockito.any()))
                .thenReturn(null);

        mockMvc.perform(put("/api/claims/1/reject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"remarks\":\"no\"}"))
                .andExpect(status().isOk());
    }
}
