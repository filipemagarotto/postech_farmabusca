package br.com.postech_farmabusca.core.domain;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private UserType role;
    private String street;
    private Integer number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private Integer zipCode;
    private String country;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
