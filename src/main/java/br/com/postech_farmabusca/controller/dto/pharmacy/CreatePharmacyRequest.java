package br.com.postech_farmabusca.controller.dto.pharmacy;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreatePharmacyRequest {

    @NotBlank(message = "Nome da farmácia é obrigatório")
    private String name;

    private String street;

    @NotNull(message = "Número da farmácia ou rua é obrigatório")
    private Integer number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;

    @NotNull(message = "CEP é obrigatório")
    private Integer zipCode;

    private String country;
}
