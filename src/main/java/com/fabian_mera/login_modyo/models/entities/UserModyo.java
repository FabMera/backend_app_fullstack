package com.fabian_mera.login_modyo.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name ="users_modyo")
@Data
public class UserModyo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String username;
    private String password;
    //Si la tabla no existe y se crea de forma automatica, se debe usar la anotacion @Column para indicar que el campo es unico
    @Column(unique = true)
    private String email;
}
