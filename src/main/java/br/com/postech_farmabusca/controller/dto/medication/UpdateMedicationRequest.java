package br.com.postech_farmabusca.controller.dto.medication;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateMedicationRequest {
    private String name;
    private String description;
    private String dosage;
    private String form;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
