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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.march.market.api.JwtRequest;
import ru.geekbrains.march.market.api.JwtResponse;
import ru.geekbrains.march.market.api.ProfileDto;
import ru.geekbrains.march.market.auth.entities.User;
import ru.geekbrains.march.market.auth.exceptions.AppError;
import ru.geekbrains.march.market.auth.services.UserService;
import ru.geekbrains.march.market.auth.utils.JwtTokenUtil;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@RestController
@RequiredArgsConstructor
@Tag(name = "Авторизация", description = "Метод авторизации пользователя в системе")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "Запрос на авторизацию",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = JwtResponse.class))
                    ),
                    @ApiResponse(
                            description = "Некорректные данные для входа", responseCode = "401",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody @Parameter(description = "Данные пользователя для авторизации", required = true) JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError("CHECK_TOKEN_ERROR", "Некорректный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Operation(
            summary = "Запрос профиля пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProfileDto.class))
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/profile")
    public ResponseEntity<?> getUserInfo(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username) {
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));
        return ResponseEntity.ok(new ProfileDto(user.getUsername(), user.getEmail(), DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(user.getCreatedAt().toLocalDate())));
    }
}
