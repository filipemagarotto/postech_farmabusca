package br.com.postech_farmabusca.controller.dto.medication;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateMedicationRequest {
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
