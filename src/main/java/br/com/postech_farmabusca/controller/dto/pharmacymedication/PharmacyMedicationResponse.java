package br.com.postech_farmabusca.controller.dto.pharmacymedication;

import lombok.Data;

@Data
public class PharmacyMedicationResponse {
    private Long pharmacyId;
    private String pharmacyName;
    private String pharmacyAddress;
    private Long medicationId;
    private String medicationName;
    private int availableStock;
}
