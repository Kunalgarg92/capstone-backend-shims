package com.policy_service;

import com.policy_service.repository.InsurancePlanRepository;
import com.policy_service.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RepositorySmokeTest {

    @Autowired
    InsurancePlanRepository insurancePlanRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    void repositories_loaded() {
        assertNotNull(insurancePlanRepository);
        assertNotNull(paymentRepository);
    }
}
