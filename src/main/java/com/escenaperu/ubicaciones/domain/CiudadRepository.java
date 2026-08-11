package com.escenaperu.ubicaciones.domain;

import java.util.List;

public interface CiudadRepository {
    List<Ciudad> findAllActivas();
}