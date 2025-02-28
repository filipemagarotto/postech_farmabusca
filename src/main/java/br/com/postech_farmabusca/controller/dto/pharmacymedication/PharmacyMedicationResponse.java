package br.com.postech_farmabusca.controller.dto.pharmacymedication;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PharmacyMedicationResponse {
    private Long id;
    private Long pharmacyId;
    private Long medicationId;
    private int stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
