package ru.geekbrains.march.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class StringResponse {
    @Schema(description = "Ответ системы", required = true, example = "Сообщение")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StringResponse() {
    }

    public StringResponse(String value) {
        this.value = value;
    }
}
