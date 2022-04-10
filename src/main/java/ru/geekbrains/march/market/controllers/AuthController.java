package ru.geekbrains.march.market.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.march.market.dtos.JwtRequest;
import ru.geekbrains.march.market.dtos.JwtResponse;
import ru.geekbrains.march.market.dtos.ProfileDto;
import ru.geekbrains.march.market.entities.User;
import ru.geekbrains.march.market.exceptions.AppError;
import ru.geekbrains.march.market.services.UserService;
import ru.geekbrains.march.market.utils.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError("CHECK_TOKEN_ERROR", "Некорректный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwt);
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return ResponseEntity.ok(new ProfileDto(user.getUsername(), user.getEmail(), DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(user.getCreatedAt().toLocalDate())));
    }
}
