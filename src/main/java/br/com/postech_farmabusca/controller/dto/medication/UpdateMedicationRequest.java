package br.com.postech_farmabusca.controller.dto.medication;

import lombok.Data;

@Data
public class UpdateMedicationRequest {
    private String name;
    private String description;
}
