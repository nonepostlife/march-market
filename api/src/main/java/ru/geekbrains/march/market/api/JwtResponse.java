package ru.geekbrains.march.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class JwtResponse {
    @Schema(description = "Токен для пользователя", required = true, example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib2IiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiZXhwIjoxNjU0MjY4ODUzLCJpYXQiOjE2NTQyMzI4NTN9.Va4E_urCfEzxNcZs0knwMYWsGYxbafwuAUzr-u5hAP0")
    private String token;

    public JwtResponse() {
    }

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
