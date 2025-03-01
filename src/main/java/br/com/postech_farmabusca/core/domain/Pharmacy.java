package br.com.postech_farmabusca.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pharmacy {
    private Long id;
    private String name;
    private String street;
    private Integer number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private Integer zipCode;
    private String country;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Pharmacy(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
