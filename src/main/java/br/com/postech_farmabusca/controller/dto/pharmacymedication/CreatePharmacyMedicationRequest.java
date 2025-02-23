package br.com.postech_farmabusca.controller.dto.pharmacymedication;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePharmacyMedicationRequest {
    @NotNull
    private Long pharmacyId;

    @NotNull
    private Long medicationId;

    @Min(0)
    private int stock;
}
