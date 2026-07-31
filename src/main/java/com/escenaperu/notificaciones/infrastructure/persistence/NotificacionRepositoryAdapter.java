package com.escenaperu.notificaciones.infrastructure.persistence;

import com.escenaperu.notificaciones.domain.Notificacion;
import com.escenaperu.notificaciones.domain.NotificacionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class NotificacionRepositoryAdapter implements NotificacionRepository {

    private final NotificacionJpaRepository jpaRepository;

    public NotificacionRepositoryAdapter(NotificacionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Notificacion save(Notificacion n) {
        var entidad = new NotificacionJpaEntity(n.getId(), n.getCiudadId(), n.getTipo(),
                n.getReferenciaId(), n.getContenido(), n.isLeido(), n.getCreatedAt());
        var guardada = jpaRepository.save(entidad);
        return new Notificacion(guardada.getId(), guardada.getCiudadId(), guardada.getTipo(),
                guardada.getReferenciaId(), guardada.getContenido(), guardada.isLeido(), guardada.getCreatedAt());
    }
}
