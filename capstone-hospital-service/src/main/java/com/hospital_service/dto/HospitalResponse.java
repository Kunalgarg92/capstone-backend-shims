package com.hospital_service.dto;

import com.hospital_service.entity.Hospital;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HospitalResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Boolean inNetwork;

    public static HospitalResponse from(Hospital hospital) {
        return new HospitalResponse(
                hospital.getId(),
                hospital.getName(),
                hospital.getEmail(),
                hospital.getPhone(),
                hospital.getAddress(),
                hospital.getInNetwork()
        );
    }
}


