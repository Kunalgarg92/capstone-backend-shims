package com.hospital_service;

import com.hospital_service.Feign.ClaimsClient;
import com.hospital_service.dto.ClaimResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClaimsClientTest {

    @Test
    void feignClient_mockedCall() {
        ClaimsClient client = Mockito.mock(ClaimsClient.class);

        Mockito.when(client.getClaimsByHospital(1L, "HOSPITAL"))
                .thenReturn(List.of(new ClaimResponse()));

        List<ClaimResponse> result = client.getClaimsByHospital(1L, "HOSPITAL");

        assertEquals(1, result.size());
    }
}
