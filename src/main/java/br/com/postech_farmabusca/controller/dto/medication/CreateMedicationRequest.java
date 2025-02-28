package br.com.postech_farmabusca.controller.dto.medication;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateMedicationRequest {
    @NotBlank(message = "O nome do medicamento é obrigatório.")
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
