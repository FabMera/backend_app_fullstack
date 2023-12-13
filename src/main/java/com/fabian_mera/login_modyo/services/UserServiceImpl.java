package com.fabian_mera.login_modyo.services;

import com.fabian_mera.login_modyo.Repositories.UserModyoRepository;
import com.fabian_mera.login_modyo.models.entities.UserModyo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class UserServiceImpl implements UserModyoService {


    //Crear constructor para inyectar el repositorio
    private final UserModyoRepository userModyoRepository;

    public UserServiceImpl(UserModyoRepository userModyoRepository) {
        this.userModyoRepository = userModyoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserModyo> findAllUsers() {
        return userModyoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserModyo> findUserById(UUID id) {
        return userModyoRepository.findById(id);
    }

    @Override
    @Transactional
    public UserModyo saveUser(UserModyo user) {
        return userModyoRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<UserModyo> updateUser(UserModyo user, UUID id) {
        Optional<UserModyo> existingUserOptional = userModyoRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            UserModyo existingUser = existingUserOptional.get();

            // Actualizar solo los campos no nulos
            if (user.getUsername() != null) {
                existingUser.setUsername(user.getUsername());
            }
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                existingUser.setPassword(user.getPassword());
            }

            return Optional.of(userModyoRepository.save(existingUser));
        }

        return Optional.empty(); // El usuario no existe
    }

    @Override
    @Transactional
    public void removeUser(UUID id) {
        userModyoRepository.deleteById(id);
    }
}
