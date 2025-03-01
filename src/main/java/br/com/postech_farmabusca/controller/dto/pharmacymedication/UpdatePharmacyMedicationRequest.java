package br.com.postech_farmabusca.controller.dto.pharmacymedication;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdatePharmacyMedicationRequest {
    @Min(value = 0, message = "O estoque não pode ser negativo")
    private int stock;
}
