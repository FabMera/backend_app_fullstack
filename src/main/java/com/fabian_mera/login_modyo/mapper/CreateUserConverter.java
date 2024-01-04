package com.fabian_mera.login_modyo.mapper;

import com.fabian_mera.login_modyo.dtos.RegisterDTO;
import com.fabian_mera.login_modyo.dtos.UpdateUserDTO;
import com.fabian_mera.login_modyo.dtos.CreateUserDTO;
import com.fabian_mera.login_modyo.models.entities.UserModyo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserConverter {

    private final ModelMapper modelMapper;

    public CreateUserDTO convertToDto(UserModyo userModyo)   {
        return modelMapper.map(userModyo, CreateUserDTO.class);
    }

    public UserModyo convertToEntity(CreateUserDTO createUserDTO) {
        return modelMapper.map(createUserDTO, UserModyo.class);
    }


}
