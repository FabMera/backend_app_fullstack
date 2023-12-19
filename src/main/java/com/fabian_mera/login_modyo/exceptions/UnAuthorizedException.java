package com.fabian_mera.login_modyo.exceptions;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
        super("No Autorizado,no tiene los permisos para realizar esta accion");
    }
}
