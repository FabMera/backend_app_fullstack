package com.fabian_mera.login_modyo.services;

import com.fabian_mera.login_modyo.dtos.RegisterDTO;
import com.fabian_mera.login_modyo.dtos.CreateUserDTO;
import com.fabian_mera.login_modyo.dtos.UpdateUserDTO;
import com.fabian_mera.login_modyo.models.entities.UserModyo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserModyoService {


    List<CreateUserDTO> findAllUsers();

    Optional<CreateUserDTO> findUserById(UUID id);

    CreateUserDTO saveUser(RegisterDTO registerDTO);

    Optional<CreateUserDTO> updateUser(UpdateUserDTO updateUserDTO, UUID id);

    void removeUser(UUID id);
    Optional<UserModyo> findUserByEmail(String email);
}
