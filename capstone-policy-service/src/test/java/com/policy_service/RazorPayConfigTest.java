package com.policy_service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.policy_service.Config.RazorPayConfig;
import com.razorpay.RazorpayClient;

class RazorPayConfigTest {

    @Test
    void razorpayClient_created_successfully() throws Exception {
        RazorPayConfig config = new RazorPayConfig();

        // inject values manually
        ReflectionTestUtils.setField(config, "keyId", "test_key");
        ReflectionTestUtils.setField(config, "keySecret", "test_secret");

        RazorpayClient client = config.razorpayClient();

        assertNotNull(client);
    }
}
