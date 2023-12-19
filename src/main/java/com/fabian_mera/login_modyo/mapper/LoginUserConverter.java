package com.fabian_mera.login_modyo.mapper;

import com.fabian_mera.login_modyo.dtos.LoginDTO;
import com.fabian_mera.login_modyo.models.entities.UserModyo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUserConverter {

    private final ModelMapper modelMapper;

    public LoginDTO convertToLoginDto(UserModyo userModyo) {
        return modelMapper.map(userModyo, LoginDTO.class);
    }

    public UserModyo convertToEntity(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, UserModyo.class);
    }

}
