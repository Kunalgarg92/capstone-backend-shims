package com.auth_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.auth_service.controller.AdminUserController;
import com.auth_service.dto.AdminCreateUserRequest;
import com.auth_service.dto.AdminUpdateUserRequest;
import com.auth_service.dto.UserResponse;
import com.auth_service.entity.Role;
import com.auth_service.service.AdminUserService;

@WebMvcTest(
    controllers = AdminUserController.class,
    excludeAutoConfiguration = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
    }
)
@AutoConfigureMockMvc(addFilters = false) // disables security filters
class AdminUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminUserService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsers_success() throws Exception {
        when(service.getAllUsers()).thenReturn(List.of(
                new UserResponse(1L, "a@test.com", "ADMIN", true)
        ));

        mockMvc.perform(get("/internal/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("a@test.com"));
    }

    @Test
    void createUser_success() throws Exception {
        AdminCreateUserRequest req =
                new AdminCreateUserRequest("a@test.com", "pass", Role.ADMIN);

        when(service.createUser(any())).thenReturn(
                new UserResponse(1L, "a@test.com", "ADMIN", true)
        );

        mockMvc.perform(post("/internal/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser_success() throws Exception {
        AdminUpdateUserRequest req =
                new AdminUpdateUserRequest(Role.CUSTOMER, false);

        when(service.updateUser(eq(1L), any()))
                .thenReturn(new UserResponse(1L, "a@test.com", "CUSTOMER", false));

        mockMvc.perform(put("/internal/admin/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser_success() throws Exception {
        doNothing().when(service).deleteUser(1L);

        mockMvc.perform(delete("/internal/admin/users/1"))
                .andExpect(status().isOk());
    }
}
