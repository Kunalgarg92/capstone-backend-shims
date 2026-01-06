package com.policy_service;

import com.policy_service.Service.PaymentService;
import com.policy_service.dto.CreateOrderRequest;
import com.policy_service.dto.VerifyPaymentRequest;
import com.policy_service.entity.Payment;
import com.policy_service.entity.PaymentStatus;
import com.policy_service.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import org.apache.commons.codec.digest.HmacUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private RazorpayClient razorpayClient;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void createOrder_success() throws Exception {
        CreateOrderRequest req = new CreateOrderRequest();

        Order order = new Order(new JSONObject(Map.of("id", "order_123")));
        Mockito.when(razorpayClient.orders.create(any(JSONObject.class)))
                .thenReturn(order);

        ReflectionTestUtils.setField(paymentService, "keyId", "test_key");

        Map<String, Object> response = paymentService.createOrder(req);

        assertEquals("order_123", response.get("orderId"));
        assertEquals(500.0, response.get("amount"));
        assertEquals("test_key", response.get("key"));
    }

    @Test
    void createOrder_exception() throws Exception {
        CreateOrderRequest req = new CreateOrderRequest();

        Mockito.when(razorpayClient.orders.create(any(JSONObject.class)))
                .thenThrow(new RuntimeException("Razorpay error"));

        assertThrows(RuntimeException.class, () -> paymentService.createOrder(req));
    }

    @Test
    void getPaymentsForPolicy_success() {
        Mockito.when(paymentRepository
                .findByPolicyIdAndUserIdOrderByCreatedAtAsc(1L, 1L))
                .thenReturn(List.of(new Payment()));

        List<Payment> payments =
                paymentService.getPaymentsForPolicy(1L, 1L);

        assertEquals(1, payments.size());
    }

    @Test
    void verifyPayment_success() {
        Payment payment = new Payment();
        payment.setRazorpayOrderId("order1");

        Mockito.when(paymentRepository.findByRazorpayOrderId("order1"))
                .thenReturn(Optional.of(payment));

        String secret = "secret";
        ReflectionTestUtils.setField(paymentService, "keySecret", secret);

        String signature = HmacUtils.hmacSha256Hex(secret, "order1|pay1");

        VerifyPaymentRequest req =
                new VerifyPaymentRequest();

        paymentService.verifyPayment(req);

        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    @Test
    void verifyPayment_invalidSignature() {
        Payment payment = new Payment();
        payment.setRazorpayOrderId("order1");

        Mockito.when(paymentRepository.findByRazorpayOrderId("order1"))
                .thenReturn(Optional.of(payment));

        ReflectionTestUtils.setField(paymentService, "keySecret", "secret");

        VerifyPaymentRequest req =
                new VerifyPaymentRequest();

        assertThrows(RuntimeException.class, () -> paymentService.verifyPayment(req));
        assertEquals(PaymentStatus.FAILED, payment.getStatus());
    }

    @Test
    void verifyPayment_notFound() {
        Mockito.when(paymentRepository.findByRazorpayOrderId(any()))
                .thenReturn(Optional.empty());

        VerifyPaymentRequest req =
                new VerifyPaymentRequest();

        assertThrows(RuntimeException.class, () -> paymentService.verifyPayment(req));
    }
}
