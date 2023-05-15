package kz.zaletov.spring.springsecurity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class AuthenticationDTO {
    @Size(min=2, message = "Не меньше 2 знаков")
    private String username;
    @Size(min=2, message = "Не меньше 2 знаков")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
