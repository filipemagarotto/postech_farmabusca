package br.com.postech_farmabusca.core.domain;

import lombok.Data;

@Data
public class PharmacyMedication {
    private  Pharmacy pharmacy;
    private Medication medication;
    private  int stock;
}
