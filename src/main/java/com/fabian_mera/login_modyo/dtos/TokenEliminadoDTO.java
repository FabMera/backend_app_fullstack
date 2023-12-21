package com.fabian_mera.login_modyo.dtos;

public class TokenEliminadoDTO {
    private String mensaje;
    public TokenEliminadoDTO(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getMensaje() {
        return mensaje;
    }
}
