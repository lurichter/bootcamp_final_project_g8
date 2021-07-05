package com.mercadolibre.group8_bootcamp_finalproject.service;


import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.AccountResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;

public interface ISessionService {

    /**
     * Perform the validation of the user and password entered.
     * If it is correct, it returns the account with the necessary token to carry out the other queries.
     *
     * @param username
     * @param password
     * @return
     * @throws NotFoundException
     */
    AccountResponseDTO login(String username, String password) throws NotFoundException;
}
