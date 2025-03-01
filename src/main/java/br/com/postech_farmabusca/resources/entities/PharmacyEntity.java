package br.com.postech_farmabusca.resources.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "pharmacy")
@Data
public class PharmacyEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String name;

    private String street;

    private Integer number;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;

    @Column(name = "zip_code", nullable = false)
    private Integer zipCode;

    private String country;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
