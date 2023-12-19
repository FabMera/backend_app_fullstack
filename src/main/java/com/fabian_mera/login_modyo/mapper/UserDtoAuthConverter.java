package com.fabian_mera.login_modyo.mapper;


import com.fabian_mera.login_modyo.dtos.UserDTOAuth;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoAuthConverter {
    private final ModelMapper modelMapper;

    public UserDTOAuth convertToEntity(UserDTOAuth userDTOAuth) {
        return modelMapper.map(userDTOAuth, UserDTOAuth.class);
    }

    public UserDTOAuth convertToDto(UserDTOAuth userDTOAuth) {
        return modelMapper.map(userDTOAuth, UserDTOAuth.class);
    }
}
