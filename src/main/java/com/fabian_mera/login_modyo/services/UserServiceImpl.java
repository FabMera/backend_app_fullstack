package com.fabian_mera.login_modyo.services;

import com.fabian_mera.login_modyo.Repositories.UserModyoRepository;
import com.fabian_mera.login_modyo.auth.Roles;
import com.fabian_mera.login_modyo.dtos.RegisterDTO;
import com.fabian_mera.login_modyo.dtos.UpdateUserDTO;
import com.fabian_mera.login_modyo.dtos.CreateUserDTO;
import com.fabian_mera.login_modyo.exceptions.EmailAlReadyExceptions;
import com.fabian_mera.login_modyo.mapper.CreateUserConverter;
import com.fabian_mera.login_modyo.mapper.RegisterUserConverter;
import com.fabian_mera.login_modyo.models.entities.UserModyo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class UserServiceImpl implements UserModyoService {


    //Crear constructor para inyectar el repositorio
    private final UserModyoRepository userModyoRepository;
    private final CreateUserConverter createUserConverter;

    private final RegisterUserConverter registerUserConverter;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserModyoRepository userModyoRepository, CreateUserConverter createUserConverter, RegisterUserConverter registerUserConverter, PasswordEncoder passwordEncoder) {
        this.userModyoRepository = userModyoRepository;
        this.createUserConverter = createUserConverter;
        this.registerUserConverter = registerUserConverter;


        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreateUserDTO> findAllUsers() {
        List<UserModyo> users = userModyoRepository.findAll();
        return users.stream().map(createUserConverter::convertToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreateUserDTO> findUserById(UUID id) {
        return userModyoRepository.findById(id).map(createUserConverter::convertToDto);
    }

    @Override
    @Transactional
    public CreateUserDTO saveUser(RegisterDTO registerDTO) {
        Optional<UserModyo> userModyo = userModyoRepository.findByEmail(registerDTO.getEmail());

        if(userModyo.isPresent()){
            throw new EmailAlReadyExceptions("El email ya existe");
        }
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());
        registerDTO.setPassword(encodedPassword);
        registerDTO.setRole(Roles.USUARIO_DEV);
        UserModyo userRegister = registerUserConverter.convertToEntity(registerDTO);
        userModyoRepository.save(userRegister);
        return createUserConverter.convertToDto(userRegister);
    }

    @Override
    @Transactional
    public Optional<CreateUserDTO> updateUser(UpdateUserDTO updateUserDTO, UUID id) {
        Optional<UserModyo> existingUserOptional = userModyoRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            UserModyo existingUser = existingUserOptional.get();

            // Actualizar solo los campos no nulos
            if (updateUserDTO.getUsername() != null) {
                existingUser.setUsername(updateUserDTO.getUsername());
            }
            if (updateUserDTO.getPassword() != null) {
                existingUser.setPassword(updateUserDTO.getPassword());
            }

            // Guardar y retornar el usuario actualizado
            UserModyo updateUser = userModyoRepository.save(existingUser);
            return Optional.of(createUserConverter.convertToDto(updateUser));
        }
        return Optional.empty(); // El usuario no existe
    }

    @Override
    @Transactional
    public void removeUser(UUID id) {
        userModyoRepository.deleteById(id);
    }

    @Override
    public Optional<UserModyo> findUserByEmail(String email) {
        return userModyoRepository.findByEmail(email);
    }

}
