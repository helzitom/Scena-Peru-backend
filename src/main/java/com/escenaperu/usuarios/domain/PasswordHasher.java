package com.escenaperu.usuarios.domain;

public interface PasswordHasher {
    String hash(String passwordPlano);
    boolean coincide(String passwordPlano, String passwordHash);
}