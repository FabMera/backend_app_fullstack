package com.fabian_mera.login_modyo.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String username;
    private String email;
    private String password;
    private String role;
}
