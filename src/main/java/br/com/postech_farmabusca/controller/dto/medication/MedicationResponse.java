package br.com.postech_farmabusca.controller.dto.medication;

import lombok.Data;

@Data
public class MedicationResponse {
    private Long id;
    private String name;
    private String description;
}
