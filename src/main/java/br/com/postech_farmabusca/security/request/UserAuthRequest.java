package br.com.postech_farmabusca.security.request;

public record UserAuthRequest(String email, String password, Boolean keepLoggedIn) {
}
