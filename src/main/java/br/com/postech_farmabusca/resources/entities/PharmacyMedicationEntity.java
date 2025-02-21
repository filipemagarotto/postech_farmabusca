package br.com.postech_farmabusca.resources.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pharmacy_medications")
@Data
public class PharmacyMedicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private PharmacyEntity pharmacy;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medication_id", nullable = false)
    private MedicationEntity medication;

    @Column(nullable = false)
    private int stock;
}
