package ru.geekbrains.march.market.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.march.market.api.JwtResponse;
import ru.geekbrains.march.market.api.RegisterUserDto;
import ru.geekbrains.march.market.api.StringResponse;
import ru.geekbrains.march.market.auth.exceptions.AppError;
import ru.geekbrains.march.market.auth.services.UserService;

@RestController
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterUserDto registerUserDto) {
        if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError("CHECK_PASSWORD_MATCH", "Указанные пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registerUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError("CHECK_USERNAME_USED",
                    "Пользователь '" + registerUserDto.getUsername() + "' уже существует"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByEmail(registerUserDto.getEmail()).isPresent()) {
            return new ResponseEntity<>(new AppError("CHECK_EMAIL_USED",
                    "Пользователь с указанной электронной почтой '" + registerUserDto.getEmail() + "' уже существует"), HttpStatus.BAD_REQUEST);
        }
        userService.registerNewUser(registerUserDto);
        return ResponseEntity.ok(new StringResponse("Пользователь успешно зарегистрирован!"));
    }
}
