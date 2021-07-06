package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {
    private String username;
    private String password;
    private String token;
}