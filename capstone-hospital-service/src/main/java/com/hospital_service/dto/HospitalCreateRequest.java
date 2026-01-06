package com.hospital_service.dto;

import lombok.Data;

@Data
public class HospitalCreateRequest {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String licenseNumber;
    private Boolean inNetwork;
}

