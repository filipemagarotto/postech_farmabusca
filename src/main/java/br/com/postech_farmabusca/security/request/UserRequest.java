package br.com.postech_farmabusca.security.request;

import br.com.postech_farmabusca.core.domain.UserType;

public record UserRequest(
        String email,
        String password,
        UserType role,
        String name,
        String street,
        Integer number,
        String complement,
        String neighborhood,
        String city,
        String state,
        Integer zipCode,
        String country
) {
}
