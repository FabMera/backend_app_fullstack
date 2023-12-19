package com.fabian_mera.login_modyo.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponseDTO {
    private  String token;

    public JwtResponseDTO(String token) {
        this.token = token;
    }
}
