package com.fabian_mera.login_modyo.controllers;

import com.fabian_mera.login_modyo.AuthLogin.AuthHelpers;
import com.fabian_mera.login_modyo.dtos.*;
import com.fabian_mera.login_modyo.exceptions.EmailAlReadyExceptions;
import com.fabian_mera.login_modyo.exceptions.PassIncorrectException;
import com.fabian_mera.login_modyo.exceptions.UserNotExistException;
import com.fabian_mera.login_modyo.mapper.CreateUserConverter;
import com.fabian_mera.login_modyo.mapper.LoginUserConverter;
import com.fabian_mera.login_modyo.services.UserModyoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(originPatterns = "*")
public class UserModyoControllers {

    private final UserModyoService userModyoService;
    private final CreateUserConverter createUserConverter;
    private final LoginUserConverter userLoginConverter;
    private final AuthHelpers authHelpers;
    private final PasswordEncoder passwordEncoder;

    public UserModyoControllers(UserModyoService userModyoService, CreateUserConverter userLoginConverter, CreateUserConverter createUserConverter, LoginUserConverter userLoginConverter1, AuthHelpers authHelpers, PasswordEncoder passwordEncoder) {
        this.userModyoService = userModyoService;
        this.createUserConverter = createUserConverter;

        this.userLoginConverter = userLoginConverter1;
        this.authHelpers = authHelpers;
        this.passwordEncoder = passwordEncoder;
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
            CreateUserDTO createUserDTO = userModyoService.saveUser(registerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createUserDTO);//201
        } catch (EmailAlReadyExceptions e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            JwtResponseDTO response = authHelpers.login(loginDTO);
            return ResponseEntity.ok(response);
        } catch (UserNotExistException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } catch (PassIncorrectException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la autenticación");
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

    /*@PostMapping("/login")
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
    }*/

}
