package com.mercadolibre.group8_bootcamp_finalproject.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "Please, insert the username.")
    String username;

    @NotBlank(message = "Please, insert the password.")
    String password;
}
