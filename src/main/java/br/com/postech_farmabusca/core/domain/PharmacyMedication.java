package br.com.postech_farmabusca.core.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PharmacyMedication {
    private Long id;
    private  Pharmacy pharmacy;
    private Medication medication;
    private  int stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
