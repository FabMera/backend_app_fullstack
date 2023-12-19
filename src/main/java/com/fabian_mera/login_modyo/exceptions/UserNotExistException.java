package com.fabian_mera.login_modyo.exceptions;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException() {
        super("El usuario no existe");
    }
}
