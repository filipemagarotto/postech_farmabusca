package br.com.postech_farmabusca.controller.dto.pharmacymedication;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdatePharmacyMedicationRequest {
    @Min(0)
    private int stock;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
