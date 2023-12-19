package com.fabian_mera.login_modyo.AuthLogin;

import com.fabian_mera.login_modyo.dtos.JwtResponseDTO;
import com.fabian_mera.login_modyo.dtos.LoginDTO;

public interface AuthHelpers {
    JwtResponseDTO login(LoginDTO loginDTO);
    JwtResponseDTO logout(String token);
}
