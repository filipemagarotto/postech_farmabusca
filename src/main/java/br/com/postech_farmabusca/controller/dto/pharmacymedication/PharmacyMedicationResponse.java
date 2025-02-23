package br.com.postech_farmabusca.controller.dto.pharmacymedication;

import lombok.Data;

@Data
public class PharmacyMedicationResponse {
    private Long id;
    private Long pharmacyId;
    private Long medicationId;
    private int stock;
}
