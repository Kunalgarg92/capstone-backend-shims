package com.user_management;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.user_management.dto.AdminCreateUserRequest;
import com.user_management.dto.AdminUpdateUserRequest;
import com.user_management.dto.UserResponse;

class DtoCoverageTest {

    @Test
    void adminCreateUserRequest_coverage() {
        AdminCreateUserRequest dto =
                new AdminCreateUserRequest("a@test.com", "pass", "ADMIN");

        assertEquals("a@test.com", dto.email());
        assertNotNull(dto.toString());
        assertEquals(dto, new AdminCreateUserRequest("a@test.com", "pass", "ADMIN"));
    }

    @Test
    void adminUpdateUserRequest_coverage() {
        AdminUpdateUserRequest dto =
                new AdminUpdateUserRequest("CUSTOMER", false);

        assertEquals("CUSTOMER", dto.role());
        assertNotNull(dto.hashCode());
    }

    @Test
    void userResponse_coverage() {
        UserResponse dto =
                new UserResponse(1L, "a@test.com", "ADMIN", true);

        assertEquals("ADMIN", dto.role());
        assertTrue(dto.toString().contains("a@test.com"));
    }
}
