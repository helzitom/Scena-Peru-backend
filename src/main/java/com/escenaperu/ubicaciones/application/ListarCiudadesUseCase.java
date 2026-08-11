package com.escenaperu.ubicaciones.application;

import com.escenaperu.ubicaciones.domain.Ciudad;
import com.escenaperu.ubicaciones.domain.CiudadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarCiudadesUseCase {

    private final CiudadRepository ciudadRepository;

    public ListarCiudadesUseCase(CiudadRepository ciudadRepository) {
        this.ciudadRepository = ciudadRepository;
    }

    public List<Ciudad> ejecutar() {
        return ciudadRepository.findAllActivas();
    }
}