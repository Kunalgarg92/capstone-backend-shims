package com.claims_service;

import com.claims_service.Feign.PolicyClient;
import com.claims_service.repository.ClaimRepository;
import com.claims_service.service.ClaimService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

class ClaimServiceConstructorTest {

    @Test
    void constructor_ioFailure_throwsRuntimeException() {
        try (var mocked = mockStatic(Files.class)) {
            mocked.when(() -> Files.exists(Mockito.any(Path.class)))
                  .thenReturn(false);

            mocked.when(() -> Files.createDirectories(Mockito.any(Path.class)))
                  .thenThrow(new java.io.IOException("disk error"));

            assertThrows(RuntimeException.class,
                    () -> new ClaimService(
                            Mockito.mock(ClaimRepository.class),
                            Mockito.mock(PolicyClient.class)));
        }
    }
}
