package com.escenaperu.ubicaciones.infrastructure.web;

import com.escenaperu.ubicaciones.application.ListarCiudadesUseCase;
import com.escenaperu.ubicaciones.infrastructure.web.dto.CiudadResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadController {

    private final ListarCiudadesUseCase listarCiudadesUseCase;

    public CiudadController(ListarCiudadesUseCase listarCiudadesUseCase) {
        this.listarCiudadesUseCase = listarCiudadesUseCase;
    }

    @GetMapping
    public List<CiudadResponse> listar() {
        return listarCiudadesUseCase.ejecutar().stream().map(CiudadResponse::desde).toList();
    }
}