package com.escenaperu.auth.application;

public record LoginResult(String accessToken, String refreshToken, long expiraEnSegundos) {

}