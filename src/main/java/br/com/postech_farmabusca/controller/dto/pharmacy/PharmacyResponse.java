package br.com.postech_farmabusca.controller.dto.pharmacy;

import lombok.Data;

@Data
public class PharmacyResponse {
    private Long id;
    private String name;
    private String address;
}
