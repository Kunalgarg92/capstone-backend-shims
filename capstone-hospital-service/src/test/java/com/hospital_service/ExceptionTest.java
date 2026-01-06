package com.hospital_service;

import org.junit.jupiter.api.Test;

import com.hospital_service.exception.BusinessException;
import com.hospital_service.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionTest {

    @Test
    void businessException_message() {
        BusinessException ex = new BusinessException("Error");
        assertEquals("Error", ex.getMessage());
    }

    @Test
    void resourceNotFoundException_message() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Not Found");
        assertEquals("Not Found", ex.getMessage());
    }
}
