package com.hospital_service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital_service.Controller.HospitalController;
import com.hospital_service.dto.ClaimResponse;
import com.hospital_service.dto.HospitalCreateRequest;
import com.hospital_service.dto.HospitalResponse;
import com.hospital_service.service.HospitalService;

import jakarta.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HospitalController.class)
class HospitalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HospitalService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createHospital_success() throws Exception {
        HospitalCreateRequest req = new HospitalCreateRequest();
        req.setName("Apollo");

        Mockito.when(service.createHospital(Mockito.any(), Mockito.eq(1L)))
                .thenReturn(new HospitalResponse(1L, "Apollo", null, null, null, true));

        mockMvc.perform(post("/api/hospitals")
                        .header("X-User-Id", 1L)
                        .header("X-User-Role", "HOSPITAL")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }


    @Test
    void getHospitals_success() throws Exception {
        Mockito.when(service.getAllHospitals())
                .thenReturn(List.of(
                        new HospitalResponse(1L, "Apollo", null, null, null, true)
                ));

        mockMvc.perform(get("/api/hospitals"))
                .andExpect(status().isOk());
    }

    @Test
    void hospitalClaims_success() throws Exception {
        Mockito.when(service.getHospitalClaims(1L, "HOSPITAL"))
                .thenReturn(List.of(new ClaimResponse()));

        mockMvc.perform(get("/api/hospitals/claims")
                        .header("X-User-Id", 1L)
                        .header("X-User-Role", "HOSPITAL"))
                .andExpect(status().isOk());
    }

    @Test
    void createHospital_invalidRole_throwsException() throws Exception {
        ServletException ex = assertThrows(
                ServletException.class,
                () -> mockMvc.perform(post("/api/hospitals")
                        .header("X-User-Id", 1L)
                        .header("X-User-Role", "USER")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                        .andReturn()
        );

        assertThat(ex.getCause())
                .isInstanceOf(RuntimeException.class);

        assertThat(ex.getCause().getMessage())
                .contains("Only hospital users can create hospital profile");
    }

    @Test
    void hospitalClaims_invalidRole_throwsException() throws Exception {
        ServletException ex = assertThrows(
                ServletException.class,
                () -> mockMvc.perform(get("/api/hospitals/claims")
                        .header("X-User-Id", 1L)
                        .header("X-User-Role", "USER"))
                        .andReturn()
        );

        assertThat(ex.getCause())
                .isInstanceOf(RuntimeException.class);

        assertThat(ex.getCause().getMessage())
                .contains("Only hospitals can view claims");
    }


}
