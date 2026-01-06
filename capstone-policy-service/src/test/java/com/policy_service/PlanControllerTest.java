package com.policy_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.policy_service.Controller.PlanController;
import com.policy_service.Service.PlanService;
import com.policy_service.dto.PlanRequest;
import com.policy_service.entity.InsurancePlan;

@WebMvcTest(
    controllers = PlanController.class,
    excludeAutoConfiguration = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
    }
)
class PlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_success() throws Exception {
        PlanRequest req = new PlanRequest();
        req.name = "Gold";
        req.coverageLimit = 100000.0;
        req.premiumAmount = 2000.0;
        req.durationYears = 2;

        when(service.create(any())).thenReturn(new InsurancePlan());

        mockMvc.perform(post("/api/admin/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void getAll_success() throws Exception {
        when(service.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/admin/plans"))
                .andExpect(status().isOk());
    }

    @Test
    void update_success() throws Exception {
        PlanRequest req = new PlanRequest();
        req.name = "Silver";
        req.coverageLimit = 50000.0;
        req.premiumAmount = 1000.0;
        req.durationYears = 1;

        when(service.update(any(), any())).thenReturn(new InsurancePlan());

        mockMvc.perform(put("/api/admin/plans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_success() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/admin/plans/1"))
                .andExpect(status().isOk());
    }
}
