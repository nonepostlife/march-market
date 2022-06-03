package ru.geekbrains.march.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class RegisterUserDto {
    @Schema(description = "Имя пользователя", required = true, example = "Bob")
    private String username;

    @Schema(description = "Пароль", required = true, example = "sev4sb45y3DD23")
    private String password;

    @Schema(description = "Подтверждение пароля", required = true, example = "sev4sb45y3DD23")
    private String confirmPassword;

    @Schema(description = "Адрес электронной почты", required = true, example = "email@domaim.com")
    private String email;

    public RegisterUserDto() {
    }

    public RegisterUserDto(String username, String password, String confirmPassword, String email) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
    }

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    // TODO возможно расширение DTO
}
