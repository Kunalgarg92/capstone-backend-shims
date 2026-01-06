
package com.policy_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
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
import com.policy_service.Controller.PolicyController;
import com.policy_service.Service.PolicyService;
import com.policy_service.dto.PolicyEnrollRequest;
import com.policy_service.entity.Policy;

@WebMvcTest(
    controllers = PolicyController.class,
    excludeAutoConfiguration = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
    }
)
class PolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PolicyService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void enroll_success() throws Exception {
        PolicyEnrollRequest req = new PolicyEnrollRequest();
        req.userId = 1L;
        req.planId = 2L;
        req.startDate = LocalDate.now();

        when(service.enroll(any())).thenReturn(new Policy());

        mockMvc.perform(post("/api/policies/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void getUserPolicies_success() throws Exception {
        when(service.getUserPolicies(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/policies/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    void renew_success() throws Exception {
        when(service.renew(1L)).thenReturn(new Policy());

        mockMvc.perform(put("/api/policies/1/renew"))
                .andExpect(status().isOk());
    }

    @Test
    void suspend_success() throws Exception {
        when(service.suspend(1L)).thenReturn(new Policy());

        mockMvc.perform(put("/api/policies/1/suspend"))
                .andExpect(status().isOk());
    }
}
