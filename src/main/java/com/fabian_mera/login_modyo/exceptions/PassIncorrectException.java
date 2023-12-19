package com.fabian_mera.login_modyo.exceptions;

public class PassIncorrectException extends RuntimeException{
    public PassIncorrectException() {
        super("La contraseña es incorrecta");
    }
}
