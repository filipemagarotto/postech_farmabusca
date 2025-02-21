package br.com.postech_farmabusca.core.domain;

import lombok.Data;

@Data
public class Medication {
    private Long id;
    private String name;
    private String description;
}