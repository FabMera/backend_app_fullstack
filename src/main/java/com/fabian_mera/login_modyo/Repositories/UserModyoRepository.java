package com.fabian_mera.login_modyo.Repositories;

import com.fabian_mera.login_modyo.models.entities.UserModyo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserModyoRepository extends JpaRepository<UserModyo, UUID> {
    Optional<UserModyo> findByEmail(String email);
}
