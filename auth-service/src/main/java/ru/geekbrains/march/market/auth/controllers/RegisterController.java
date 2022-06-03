package ru.geekbrains.march.market.auth.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.march.market.api.RegisterUserDto;
import ru.geekbrains.march.market.api.StringResponse;
import ru.geekbrains.march.market.auth.exceptions.AppError;
import ru.geekbrains.march.market.auth.services.UserService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Регистрация", description = "Метод регистрации нового пользователя")
public class RegisterController {
    private final UserService userService;

    @Operation(
            summary = "Запрос на регистрацию нового пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    ),
                    @ApiResponse(
                            description = "Ошибка при регистрации пользователя", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody @Parameter(description = "Данные нового пользователя", required = true) RegisterUserDto registerUserDto) {
        userService.registerNewUser(registerUserDto);
        return new ResponseEntity<>(new StringResponse("Пользователь успешно зарегистрирован!"), HttpStatus.CREATED);
    }
}
