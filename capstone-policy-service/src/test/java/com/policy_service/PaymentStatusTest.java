package com.policy_service;

import org.junit.jupiter.api.Test;

import com.policy_service.entity.PaymentStatus;

import static org.junit.jupiter.api.Assertions.*;

class PaymentStatusTest {

    @Test
    void paymentStatus_enum_coverage() {
        PaymentStatus status = PaymentStatus.valueOf("SUCCESS");
        assertEquals(PaymentStatus.SUCCESS, status);

        assertEquals(3, PaymentStatus.values().length);
    }
}
