package com.escenaperu.auth.application;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException() {
        super("Refresh token invalido o expirado");
    }
}