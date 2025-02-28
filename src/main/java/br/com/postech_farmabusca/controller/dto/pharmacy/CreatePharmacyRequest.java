package br.com.postech_farmabusca.controller.dto.pharmacy;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreatePharmacyRequest {
    @NotBlank
    private String name;
    private String street;
    private Integer number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    @NotBlank
    private Integer zipCode;
    private String country;
}
