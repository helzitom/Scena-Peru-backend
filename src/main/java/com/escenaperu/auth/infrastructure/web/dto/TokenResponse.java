package com.escenaperu.auth.infrastructure.web.dto;

import com.escenaperu.auth.application.LoginResult;

public record TokenResponse(String accessToken, String refreshToken, long expiraEnSegundos) {
    public static TokenResponse desde(LoginResult r) {
        return new TokenResponse(r.accessToken(), r.refreshToken(), r.expiraEnSegundos());
    }
}