package br.com.postech_farmabusca.controller.dto.pharmacy;

import lombok.Data;

@Data
public class UpdatePharmacyRequest {
    private String name;
    private String address;
}
