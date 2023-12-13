package com.fabian_mera.login_modyo.services;

import com.fabian_mera.login_modyo.models.entities.UserModyo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserModyoService {

    List<UserModyo> findAllUsers();
    Optional<UserModyo> findUserById(UUID id);
    UserModyo saveUser(UserModyo user);
    Optional<UserModyo> updateUser(UserModyo user, UUID id);
    void removeUser(UUID id);

}
