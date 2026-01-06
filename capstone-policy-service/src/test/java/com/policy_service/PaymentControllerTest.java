package com.policy_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.policy_service.Controller.PaymentController;
import com.policy_service.Service.PaymentService;
import com.policy_service.dto.CreateOrderRequest;
import com.policy_service.dto.VerifyPaymentRequest;

@WebMvcTest(
    controllers = PaymentController.class,
    excludeAutoConfiguration = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
    }
)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder_success() throws Exception {
        CreateOrderRequest req = new CreateOrderRequest();
        req.setPolicyId(1L);
        req.setUserId(2L);
        req.setAmount(1000.0);

        when(paymentService.createOrder(any()))
                .thenReturn(Map.of("orderId", "order123"));

        mockMvc.perform(post("/api/payments/create-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("order123"));
    }

    @Test
    void verifyPayment_success() throws Exception {
        VerifyPaymentRequest req = new VerifyPaymentRequest();
        req.setRazorpayOrderId("oid");
        req.setRazorpayPaymentId("pid");
        req.setRazorpaySignature("sig");

        doNothing().when(paymentService).verifyPayment(any());

        mockMvc.perform(post("/api/payments/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment verified successfully"));
    }

    @Test
    void getPaymentsForPolicy_success() throws Exception {
        when(paymentService.getPaymentsForPolicy(1L, 2L))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/payments/policy/1/user/2"))
                .andExpect(status().isOk());
    }
}

