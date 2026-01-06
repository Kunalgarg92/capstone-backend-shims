package com.claims_service;

import com.claims_service.Controller.ClaimInternalController;
import com.claims_service.service.ClaimService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClaimInternalController.class)
class ClaimInternalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClaimService service;

    @Test
    void getHospitalClaims_success() throws Exception {
        Mockito.when(service.getClaimsByHospital(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/internal/claims/hospital/claims")
                        .header("X-User-Id", 1L)
                        .header("X-User-Role", "HOSPITAL"))
                .andReturn();
    }

    @Test
    void getHospitalClaims_invalidRole() throws Exception {
        mockMvc.perform(get("/api/internal/claims/hospital/claims")
                        .header("X-User-Id", 1L)
                        .header("X-User-Role", "USER"))
                .andExpect(status().isBadRequest());
    }

}
