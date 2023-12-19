package com.fabian_mera.login_modyo.services;

import com.fabian_mera.login_modyo.AuthLogin.AuthHelpers;
import com.fabian_mera.login_modyo.Repositories.UserModyoRepository;
import com.fabian_mera.login_modyo.auth.JWTAuthenticationProvider;
import com.fabian_mera.login_modyo.dtos.JwtResponseDTO;
import com.fabian_mera.login_modyo.dtos.LoginDTO;
import com.fabian_mera.login_modyo.exceptions.PassIncorrectException;
import com.fabian_mera.login_modyo.exceptions.UserNotExistException;
import com.fabian_mera.login_modyo.models.entities.UserModyo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthHelpers {

    private final UserModyoRepository userModyoRepository;
    private final JWTAuthenticationProvider jwtAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    //Verificamos credenciales que recibimos del cliente y si son correctas, generamos un token
    public JwtResponseDTO login(LoginDTO loginDTO) {
        Optional<UserModyo> userModyo = userModyoRepository.findByEmail(loginDTO.getEmail());
        if (userModyo.isEmpty()) {
            throw new UserNotExistException();
        }
        if (!passwordEncoder.matches(loginDTO.getPassword(), userModyo.get().getPassword())) {
            throw new PassIncorrectException();
        }
        return new JwtResponseDTO(jwtAuthenticationProvider.createToken(userModyo.get()));

    }

    @Override
    public JwtResponseDTO logout(String token) {
        String[] tokenSplit = token.split(" ");
        return new JwtResponseDTO(jwtAuthenticationProvider.eliminarToken(tokenSplit[1]));
    }
}
