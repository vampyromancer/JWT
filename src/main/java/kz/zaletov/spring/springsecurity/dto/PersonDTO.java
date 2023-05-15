package kz.zaletov.spring.springsecurity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class PersonDTO {

    @Size(min=2, message = "Не меньше 2 знаков")
    private String username;
    @Min(value = 0)
    private int age;
    @Size(min=2, message = "Не меньше 2 знаков")
    private String password;
    private String passwordConfirm;

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
