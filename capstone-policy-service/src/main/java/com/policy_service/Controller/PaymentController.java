package com.policy_service.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.policy_service.dto.CreateOrderRequest;
import com.policy_service.dto.VerifyPaymentRequest;
import com.policy_service.Service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // CREATE ORDER
    @PostMapping("/create-order")
    public ResponseEntity<Map<String, Object>> createOrder(
            @Valid @RequestBody CreateOrderRequest req) throws Exception {

        return ResponseEntity.ok(paymentService.createOrder(req));
    }

    // VERIFY PAYMENT
    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(
            @Valid @RequestBody VerifyPaymentRequest req) {

        paymentService.verifyPayment(req);
        return ResponseEntity.ok("Payment verified successfully");
    }
    
    @GetMapping("/policy/{policyId}/user/{userId}")
    public ResponseEntity<?> getPaymentsForPolicy(
            @PathVariable Long policyId,
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                paymentService.getPaymentsForPolicy(policyId, userId)
        );
    }

}

