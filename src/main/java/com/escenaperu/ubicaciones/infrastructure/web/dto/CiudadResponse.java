package com.escenaperu.ubicaciones.infrastructure.web.dto;

import com.escenaperu.ubicaciones.domain.Ciudad;

public record CiudadResponse(Integer id, String nombre, String departamento) {
    public static CiudadResponse desde(Ciudad c) {
        return new CiudadResponse(c.getId(), c.getNombre(), c.getDepartamento());
    }
}