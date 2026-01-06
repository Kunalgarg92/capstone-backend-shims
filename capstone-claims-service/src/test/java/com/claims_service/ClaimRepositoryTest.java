package com.claims_service;

import com.claims_service.entity.Claim;
import com.claims_service.entity.ClaimStatus;
import com.claims_service.repository.ClaimRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClaimRepositoryTest {

    @Autowired
    private ClaimRepository repo;

    @Test
    void repositoryMethods_exist() {
        Claim c = new Claim();
        c.setStatus(ClaimStatus.SUBMITTED);
        repo.save(c);

        assertEquals(1, repo.findByStatus(ClaimStatus.SUBMITTED).size());
    }
}
