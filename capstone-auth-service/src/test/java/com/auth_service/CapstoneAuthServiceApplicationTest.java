package com.auth_service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class CapstoneAuthServiceApplicationTest {

    @Test
    void main_runsSuccessfully() {
        assertDoesNotThrow(() ->
                CapstoneAuthServiceApplication.main(new String[] {}));
    }
}
