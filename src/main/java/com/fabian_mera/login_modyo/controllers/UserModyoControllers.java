package com.fabian_mera.login_modyo.controllers;

import com.fabian_mera.login_modyo.dtos.LoginDTO;
import com.fabian_mera.login_modyo.dtos.RegisterDTO;
import com.fabian_mera.login_modyo.dtos.CreateUserDTO;
import com.fabian_mera.login_modyo.dtos.UpdateUserDTO;
import com.fabian_mera.login_modyo.mapper.CreateUserConverter;
import com.fabian_mera.login_modyo.models.entities.UserModyo;
import com.fabian_mera.login_modyo.services.UserModyoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserModyoControllers {

    private final UserModyoService userModyoService;
    private final CreateUserConverter userLoginConverter;

    public UserModyoControllers(UserModyoService userModyoService, CreateUserConverter userLoginConverter) {
        this.userModyoService = userModyoService;
        this.userLoginConverter = userLoginConverter;
    }

    @GetMapping
    public List<CreateUserDTO> list() {
        return userModyoService.findAllUsers();//200
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable UUID id) {
        Optional<CreateUserDTO> userOptional = userModyoService.findUserById(id);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();//404
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody RegisterDTO registerDTO) {
        try {
            CreateUserDTO savedUser = userModyoService.saveUser(registerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (DataIntegrityViolationException e) {
            // En caso de que se lance una excepción de clave duplicada (correo ya existente)

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Correo electrónico ya existe");

        } catch (Exception e) {
            // Manejo general de excepciones
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar la solicitud");
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody UpdateUserDTO updateUserDTO, @PathVariable UUID id) {
        Optional<CreateUserDTO> updatedUser = userModyoService.updateUser(updateUserDTO, id);

        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(updatedUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable UUID id) {
        Optional<CreateUserDTO> op = userModyoService.findUserById(id);
        if (op.isPresent()) {
            userModyoService.removeUser(id);
            return ResponseEntity.noContent().build();//204
        }
        return ResponseEntity.notFound().build();//404
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        // Buscar usuario por correo electrónico EN LA BASE DE DATOS

        Optional<UserModyo> userOptional = userModyoService.findUserByEmail(loginDTO.getEmail());
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(loginDTO.getPassword())) {
            // La autenticación fue exitosa, devuelve algún tipo de token o información de usuario
            return ResponseEntity.ok(userOptional.get());
        } else {
            // La autenticación falló, devuelve un error
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario y/o contraseña inválida");
        }
    }

}
