package com.fabian_mera.login_modyo.mapper;


import com.fabian_mera.login_modyo.dtos.UserDTOAuth;
import com.fabian_mera.login_modyo.models.entities.UserModyo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoAuthConverter {
    private final ModelMapper modelMapper;

    public UserDTOAuth convertToDto(UserModyo userModyo) {
        return modelMapper.map(userModyo, UserDTOAuth.class);
    }

    public UserModyo convertToEntity(UserDTOAuth userDTOAuth) {
        return modelMapper.map(userDTOAuth, UserModyo.class);
    }
}
