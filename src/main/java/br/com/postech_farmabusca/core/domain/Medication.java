package br.com.postech_farmabusca.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medication {
    private Long id;
    private String name;
    private String description;
    private String dosage;
    private String form;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Medication(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
