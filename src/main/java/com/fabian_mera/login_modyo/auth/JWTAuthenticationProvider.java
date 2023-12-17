package com.fabian_mera.login_modyo.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class JWTAuthenticationProvider {

    //llave secreta
    @Value("${jwt.secret.key}")
    private String secretKey;


    //Lista de tokens
    private HashMap<String,CustomerDTO>
}
