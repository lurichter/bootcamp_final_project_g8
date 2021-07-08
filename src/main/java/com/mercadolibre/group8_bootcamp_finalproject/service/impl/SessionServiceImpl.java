package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.AccountResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.ApiException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.Users;
import com.mercadolibre.group8_bootcamp_finalproject.repository.UsersRepository;
import com.mercadolibre.group8_bootcamp_finalproject.security.JWTAuthorizationFilter;
import com.mercadolibre.group8_bootcamp_finalproject.service.ISessionService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements ISessionService {
    private final UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder encoder;

//    /**
//     * Perform the validation of the user and password entered.
//     * If correct, return the account with the necessary token to carry out the other queries.
//     *
//     * @param username
//     * @param password
//     * @return
//                            //     * @throws NotFoundException
//     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public AccountResponseDTO login(String username, String password) throws ApiException {
        String passEncoded = encoder.encode(password);

        Users account = usersRepository.findByName(username);

        if ( account.getName().isBlank() ) throw new NotFoundException("User not found");

        boolean isPassword = encoder.matches(account.getPassword(), passEncoded);

        if ( !isPassword ) throw new NotFoundException("Incorrect password");

        if (account != null) {
            String token = getJWTToken(username);
            AccountResponseDTO user = new AccountResponseDTO();
            user.setUsername(username);
            user.setToken(token);
            return user;
        } else {
            throw new ApiException("404", "Incorrect username and/or password", 404);
        }

    }

    /**
     * Generate a token for a specific user, valid for 10'
     * @param username
     * @return
     */
    private String getJWTToken(String username) {
        String secretKey = JWTAuthorizationFilter.SECRET;
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    /**
     * Decode a token in order to obtain the components it contains
     * @param token
     * @return
     */
    private static Claims decodeJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWTAuthorizationFilter.SECRET.getBytes())
                .parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * It allows to obtain the username according to the indicated token
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        Claims claims = decodeJWT(token);
        return claims.get("sub", String.class);
    }

}
