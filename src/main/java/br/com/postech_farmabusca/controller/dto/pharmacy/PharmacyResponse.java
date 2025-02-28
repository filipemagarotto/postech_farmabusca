package br.com.postech_farmabusca.controller.dto.pharmacy;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PharmacyResponse {
    private Long id;
    private String name;
    private String street;
    private Integer number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private Integer zipCode;
    private String country;
}
