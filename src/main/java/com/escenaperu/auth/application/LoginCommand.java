package com.escenaperu.auth.application;

public record LoginCommand(
        String email, String password) {

}