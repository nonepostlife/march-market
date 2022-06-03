package ru.geekbrains.march.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProfileDto {
    @Schema(description = "Имя пользователя", required = true, example = "Bob")
    private String username;

    @Schema(description = "Адрес электронной почты", required = true, example = "email@domaim.com")
    private String email;

    @Schema(description = "Дата регистрации пользователя", required = true, example = "3 июн. 2022 г.")
    private String registrationDate;

    public ProfileDto() {
    }

    public ProfileDto(String username, String email, String registrationDate) {
        this.username = username;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
