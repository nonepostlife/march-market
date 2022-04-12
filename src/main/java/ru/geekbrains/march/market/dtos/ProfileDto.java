package ru.geekbrains.march.market.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileDto {
    private String username;
    private String email;
    private String registrationDate;
}
