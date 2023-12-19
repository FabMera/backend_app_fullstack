package com.fabian_mera.login_modyo.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fabian_mera.login_modyo.dtos.UserDTOAuth;
import com.fabian_mera.login_modyo.models.entities.UserModyo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Date;
import java.util.HashSet;


@Component
public class JWTAuthenticationProvider {

    //llave secreta
    @Value("${jwt.secret.key}")
    private String secretKey;


    //Lista de tokens
    private final HashMap<String, UserDTOAuth> listaTokens = new HashMap<>();

    //Método para crear el token
    public String createToken(UserDTOAuth userDTOAuth) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Date date = new Date();
        Date validDate = new Date(date.getTime() + 3600000);
        String token = JWT.create()
                .withClaim("username", userDTOAuth.getUsername())
                .withClaim("email", userDTOAuth.getEmail())
                .withClaim("rol", userDTOAuth.getRol())
                .withIssuedAt(date)
                .withExpiresAt(validDate)
                .sign(algorithm);
        listaTokens.put(token, userDTOAuth);
        return token;
    }

    //Validar el token
    public Authentication validateToken(String jwtoken) {
        JWT.require(Algorithm.HMAC256(secretKey)).build().verify(jwtoken);
        UserDTOAuth existingUserToken = listaTokens.get(jwtoken);
        if (existingUserToken == null) {
            throw new BadCredentialsException("Token no válido");
        }
        HashSet<SimpleGrantedAuthority> rolesAuthorities = new HashSet<>();
        rolesAuthorities.add(new SimpleGrantedAuthority("ROLE" + existingUserToken.getRol()));
        return new UsernamePasswordAuthenticationToken(existingUserToken, jwtoken, rolesAuthorities);
    }

    //Eliminar el token
    public String eliminarToken(String jwtoken) {
        if (!listaTokens.containsKey(jwtoken)) {
            throw new BadCredentialsException("Token no válido");
        }
        listaTokens.remove(jwtoken);
        return "Token eliminado";
    }
}
