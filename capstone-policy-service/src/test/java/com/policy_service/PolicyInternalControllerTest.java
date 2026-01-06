package com.policy_service;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.policy_service.Controller.PolicyInternalController;
import com.policy_service.entity.Policy;
import com.policy_service.entity.PolicyStatus;
import com.policy_service.repository.PolicyRepository;

@WebMvcTest(
    controllers = PolicyInternalController.class,
    excludeAutoConfiguration = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
    }
)
class PolicyInternalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PolicyRepository repository;

    @Test
    void validatePolicy_owned_and_active() throws Exception {
        Policy policy = new Policy();
        policy.setId(1L);
        policy.setUserId(2L);
        policy.setStatus(PolicyStatus.ACTIVE);

        when(repository.findByIdAndUserId(1L, 2L))
                .thenReturn(Optional.of(policy));

        mockMvc.perform(get("/api/internal/policies/1/validate/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void validatePolicy_not_owned() throws Exception {
        when(repository.findByIdAndUserId(1L, 2L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/internal/policies/1/validate/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(false))
                .andExpect(jsonPath("$.status").value("NOT_OWNED"));
    }
}

