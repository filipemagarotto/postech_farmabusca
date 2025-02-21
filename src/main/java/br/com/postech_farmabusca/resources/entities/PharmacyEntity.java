package br.com.postech_farmabusca.resources.entities;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "pharmacy")
@Data
public class PharmacyEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

}
