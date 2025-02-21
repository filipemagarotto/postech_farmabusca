package br.com.postech_farmabusca.controller.dto.medication;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateMedicationRequest {
    @NotBlank
    private String name;
    private String description;
}
