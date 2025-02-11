package br.com.postech_farmabusca.controller.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Integer id,
        String name,
        String email,
        String password,
        String role,
        String street,
        Integer number,
        String complement,
        String neighborhood,
        String city,
        String state,
        Integer zipCode,
        String country,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
