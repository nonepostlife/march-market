package ru.geekbrains.march.market.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.march.market.api.RegisterUserDto;
import ru.geekbrains.march.market.auth.entities.Role;
import ru.geekbrains.march.market.auth.entities.User;
import ru.geekbrains.march.market.auth.exceptions.RegistrationException;
import ru.geekbrains.march.market.auth.repositories.UserRepository;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void registerNewUser(RegisterUserDto registerUserDto) {
        if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            throw new RegistrationException("Указанные пароли не совпадают");
        }
        if (findByUsername(registerUserDto.getUsername()).isPresent()) {
            throw new RegistrationException("Пользователь '" + registerUserDto.getUsername() + "' уже существует");
        }
        if (findByEmail(registerUserDto.getEmail()).isPresent()) {
            throw new RegistrationException("Пользователь с указанной электронной почтой '" + registerUserDto.getEmail() + "' уже существует");
        }
        if (!emailValidate(registerUserDto.getEmail())) {
            throw new RegistrationException("Указанная электронная почта '" + registerUserDto.getEmail() + "' имеет неверный формат");
        }

        String bcryptCachedPassword = passwordEncoder.encode(registerUserDto.getPassword());
        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(bcryptCachedPassword);
        user.setEmail(registerUserDto.getEmail());
        user.setRoles(List.of(roleService.getUserRole()));
        userRepository.save(user);
    }

    private boolean emailValidate(String emailAddress) {
        return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                .matcher(emailAddress)
                .matches();
    }
}