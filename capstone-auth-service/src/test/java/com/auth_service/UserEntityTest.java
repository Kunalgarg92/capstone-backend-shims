package com.auth_service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.auth_service.entity.Role;
import com.auth_service.entity.User;

class UserEntityTest {

    @Test
    void equalsAndHashCode_covered() {
        User u1 = new User();
        u1.setId(1L);
        u1.setEmail("a@test.com");
        u1.setPassword("pass");
        u1.setRole(Role.ADMIN);
        u1.setActive(true);

        User u2 = new User();
        u2.setId(1L);
        u2.setEmail("a@test.com");
        u2.setPassword("pass");
        u2.setRole(Role.ADMIN);
        u2.setActive(true);

        assertEquals(u1, u2);           // equals()
        assertEquals(u1.hashCode(), u2.hashCode()); // hashCode()
    }

    @Test
    void toString_covered() {
        User user = new User();
        user.setEmail("a@test.com");

        String result = user.toString();

        assertNotNull(result);
        assertTrue(result.contains("a@test.com"));
    }
}

