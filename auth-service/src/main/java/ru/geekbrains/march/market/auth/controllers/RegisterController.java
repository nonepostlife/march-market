package ru.geekbrains.march.market.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.march.market.api.RegisterUserDto;
import ru.geekbrains.march.market.api.StringResponse;
import ru.geekbrains.march.market.auth.services.UserService;

@RestController
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterUserDto registerUserDto) {
        userService.registerNewUser(registerUserDto);
        return ResponseEntity.ok(new StringResponse("Пользователь успешно зарегистрирован!"));
    }
}
