package br.com.postech_farmabusca.controller.dto.pharmacy;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePharmacyRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
}
