package com.fabian_mera.login_modyo.mapper;

import com.fabian_mera.login_modyo.dtos.RegisterDTO;
import com.fabian_mera.login_modyo.models.entities.UserModyo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterUserConverter {

    private final ModelMapper modelMapper;
    public UserModyo convertToEntity(RegisterDTO registerDTO) {
        return modelMapper.map(registerDTO, UserModyo.class);

    }

    public RegisterDTO convertToLoginDto(UserModyo userModyo) {
        return modelMapper.map(userModyo, RegisterDTO.class);
    }
}
