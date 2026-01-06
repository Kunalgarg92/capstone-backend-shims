package com.policy_service.Service;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.HmacUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.policy_service.dto.CreateOrderRequest;
import com.policy_service.dto.VerifyPaymentRequest;
import com.policy_service.entity.Payment;
import com.policy_service.entity.PaymentStatus;
import com.policy_service.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class PaymentService {

    private final RazorpayClient razorpayClient;
    private final PaymentRepository paymentRepository;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public PaymentService(RazorpayClient razorpayClient, PaymentRepository paymentRepository) {
        this.razorpayClient = razorpayClient;
        this.paymentRepository = paymentRepository;
    }

    public Map<String, Object> createOrder(CreateOrderRequest req) {

        try {
            System.out.println("Create order request: " + req);
            System.out.println("Amount received: " + req.getAmount());

            JSONObject options = new JSONObject();
            options.put("amount", (int) (req.getAmount() * 100));
            options.put("currency", "INR");
            options.put("receipt", "POLICY_" + req.getPolicyId());

            Order order = razorpayClient.orders.create(options);

            Payment payment = new Payment();
            payment.setPolicyId(req.getPolicyId());
            payment.setUserId(req.getUserId());
            payment.setAmount(req.getAmount());
            payment.setRazorpayOrderId(order.get("id"));
            payment.setStatus(PaymentStatus.CREATED);

            paymentRepository.save(payment);

            return Map.of(
                "orderId", order.get("id"),
                "amount", req.getAmount(),
                "key", keyId
            );

        } catch (Exception e) {
            e.printStackTrace();   // 🔥 THIS WILL SHOW REAL ERROR
            throw new RuntimeException("Failed to create Razorpay order");
        }
    }

    
    public List<Payment> getPaymentsForPolicy(Long policyId, Long userId) {
        return paymentRepository
                .findByPolicyIdAndUserIdOrderByCreatedAtAsc(policyId, userId);
    }


    public void verifyPayment(VerifyPaymentRequest req) {

        Payment payment = paymentRepository
                .findByRazorpayOrderId(req.getRazorpayOrderId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        String generatedSignature = HmacUtils.hmacSha256Hex(
                keySecret,
                req.getRazorpayOrderId() + "|" + req.getRazorpayPaymentId()
        );

        if (!generatedSignature.equals(req.getRazorpaySignature())) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            throw new RuntimeException("Invalid payment signature");
        }

        payment.setRazorpayPaymentId(req.getRazorpayPaymentId());
        payment.setRazorpaySignature(req.getRazorpaySignature());
        payment.setStatus(PaymentStatus.SUCCESS);

        paymentRepository.save(payment);
    }
}

