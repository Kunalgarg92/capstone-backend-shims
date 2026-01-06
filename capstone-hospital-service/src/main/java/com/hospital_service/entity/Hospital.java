package com.hospital_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hospitals")
@Data
public class Hospital {

    @Id
    private Long id; 

    private String name;
    private String email;
    private String phone;
    private String address;
    private String licenseNumber;
    private Boolean inNetwork;
}


