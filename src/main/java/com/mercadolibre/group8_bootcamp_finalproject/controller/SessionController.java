package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.LoginRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.AccountResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.service.ISessionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(path = "/api/v1")
@RestController
public class SessionController {
    private final ISessionService service;

    public SessionController(ISessionService sessionService) {
        this.service = sessionService;
    }

    /**
     * Perform the validation of the user and password entered.
     * If correct, return the account with the necessary token to carry out the other queries.
     *
     * @param loginRequestDTO
     * @return AccountResponseDTO
     * @throws NotFoundException
     */
    @PostMapping("/sign-in")
    public AccountResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) throws NotFoundException {
        return service.login(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
    }

}
