package com.fabian_mera.login_modyo.mapper;

import com.fabian_mera.login_modyo.dtos.UpdateUserDTO;
import com.fabian_mera.login_modyo.models.entities.UserModyo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserConverter {

    private final ModelMapper modelMapper;

    public UpdateUserDTO convertToUpdateUserDto(UserModyo userModyo) {
        return modelMapper.map(userModyo, UpdateUserDTO.class);
    }

    public UserModyo convertToEntity(UpdateUserDTO updateUserDTO) {
        return modelMapper.map(updateUserDTO, UserModyo.class);
    }
}
