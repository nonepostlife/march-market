package ru.geekbrains.march.market.auth.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.march.market.api.CartItemDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppError {
    @Schema(description = "Код ошибки", required = true, example = "CHECK_TOKEN_ERROR")
    private String code;

    @Schema(description = "Текст ошибки", required = true, example = "Некорректный логин или пароль")
    private String message;
}
