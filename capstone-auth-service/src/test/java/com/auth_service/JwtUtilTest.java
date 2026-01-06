package com.auth_service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.auth_service.Security.JwtUtil;

class JwtUtilTest {

    @Test
    void generateToken_success() {
        JwtUtil util = new JwtUtil();
        ReflectionTestUtils.setField(util, "secret", "12345678901234567890123456789012");
        ReflectionTestUtils.setField(util, "expiration", 100000);

        String token = util.generateToken(1L, "ADMIN");

        assertNotNull(token);
    }
}

