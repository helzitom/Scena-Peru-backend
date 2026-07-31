package com.escenaperu.notificaciones.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface NotificacionJpaRepository extends JpaRepository<NotificacionJpaEntity, UUID> { }
