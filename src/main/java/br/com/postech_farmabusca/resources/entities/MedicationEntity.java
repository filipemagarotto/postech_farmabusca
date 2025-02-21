package br.com.postech_farmabusca.resources.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "medications")
@Data
public class MedicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

}