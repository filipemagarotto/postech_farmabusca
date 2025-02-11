package br.com.postech_farmabusca.core.domain;

public enum UserType {
    ADMIN("admin"),
    USER("user");

    private String role;

    UserType(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
