package br.com.postech_farmabusca.core.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pharmacy {
    private  Long id;
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
}
