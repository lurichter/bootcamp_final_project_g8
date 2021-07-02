package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.AccountResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.service.ISessionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @param username
     * @param password
     * @return
     * @throws NotFoundException
     */
    @PostMapping("/sign-in")
    public AccountResponseDTO login(@RequestParam("username") String username, @RequestParam("password") String password) throws NotFoundException {
        return service.login(username, password);
    }

}
