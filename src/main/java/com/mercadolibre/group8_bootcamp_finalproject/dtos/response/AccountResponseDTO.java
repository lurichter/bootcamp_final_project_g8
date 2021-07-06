package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponseDTO {
    private String username;
    private String password;
    private String token;
}