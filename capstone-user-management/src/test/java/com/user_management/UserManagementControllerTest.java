package com.user_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.user_management.Controller.UserManagementController;
import com.user_management.Feign.AuthClient;
import com.user_management.dto.AdminCreateUserRequest;
import com.user_management.dto.AdminUpdateUserRequest;
import com.user_management.dto.UserResponse;

@WebMvcTest(
    controllers = UserManagementController.class,
    excludeAutoConfiguration = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
    }
)
@AutoConfigureMockMvc(addFilters = false)
class UserManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthClient authClient;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- GET ALL USERS ----------

    @Test
    void getAll_asAdmin_success() throws Exception {
        when(authClient.getAllUsers())
                .thenReturn(List.of(new UserResponse(1L, "a@test.com", "ADMIN", true)));

        mockMvc.perform(get("/api/admin/users")
                        .header("X-User-Role", "ADMIN"))
                .andExpect(status().isOk());
    }

    @Test
    void getAll_asAgent_success() throws Exception {
        when(authClient.getAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/api/admin/users")
                        .header("X-User-Role", "INSURANCE_AGENT"))
                .andExpect(status().isOk());
    }

    @Test
    void getAll_forbidden() {
        Exception ex = assertThrows(
                Exception.class,
                () -> mockMvc.perform(
                        get("/api/admin/users")
                                .header("X-User-Role", "CUSTOMER")
                ).andReturn()
        );

        assertTrue(ex.getCause() instanceof RuntimeException);
        assertEquals(
                "Forbidden: Admin or Insurance Agent access required",
                ex.getCause().getMessage()
        );
    }



    // ---------- CREATE USER ----------

    @Test
    void create_asAdmin_success() throws Exception {
        AdminCreateUserRequest req =
                new AdminCreateUserRequest("a@test.com", "pass", "ADMIN");

        when(authClient.createUser(any()))
                .thenReturn(new UserResponse(1L, "a@test.com", "ADMIN", true));

        mockMvc.perform(post("/api/admin/users")
                        .header("X-User-Role", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void create_forbidden() throws Exception {
        AdminCreateUserRequest req =
                new AdminCreateUserRequest("a@test.com", "pass", "ADMIN");

        Exception ex = assertThrows(
                Exception.class,
                () -> mockMvc.perform(
                        post("/api/admin/users")
                                .header("X-User-Role", "CUSTOMER")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                ).andReturn()
        );

        assertTrue(ex.getCause() instanceof RuntimeException);
        assertEquals(
                "Forbidden: Admin access required",
                ex.getCause().getMessage()
        );
    }



    // ---------- UPDATE USER ----------

    @Test
    void update_asAdmin_success() throws Exception {
        AdminUpdateUserRequest req =
                new AdminUpdateUserRequest("CUSTOMER", false);

        when(authClient.updateUser(eq(1L), any()))
                .thenReturn(new UserResponse(1L, "a@test.com", "CUSTOMER", false));

        mockMvc.perform(put("/api/admin/users/1")
                        .header("X-User-Role", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    // ---------- DELETE USER ----------

    @Test
    void delete_asAdmin_success() throws Exception {
        doNothing().when(authClient).deleteUser(1L);

        mockMvc.perform(delete("/api/admin/users/1")
                        .header("X-User-Role", "ADMIN"))
                .andExpect(status().isOk());
    }
}
