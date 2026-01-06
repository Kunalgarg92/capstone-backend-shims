package com.claims_service;

import com.claims_service.entity.Policy;
import com.claims_service.repository.PolicyRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PolicyRepositoryTest {

    @Autowired
    private PolicyRepository repo;

    @Test
    void existsByIdAndUserId() {
        Policy p = new Policy();
        p.setId(1L);
        p.setUserId(2L);
        p.setStatus("ACTIVE");

        repo.save(p);

        assertTrue(repo.existsByIdAndUserId(1L, 2L));
    }
}
