package com.mercadolibre.group8_bootcamp_finalproject.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String getLoggedUser(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
