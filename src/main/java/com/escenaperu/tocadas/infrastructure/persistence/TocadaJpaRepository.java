package com.escenaperu.tocadas.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TocadaJpaRepository extends JpaRepository<TocadaJpaEntity, UUID> {

    @Query("SELECT t FROM TocadaJpaEntity t WHERE t.ciudadId = :ciudadId AND t.fecha >= :desde ORDER BY t.fecha ASC")
    List<TocadaJpaEntity> buscarPorCiudadDesde(@Param("ciudadId") Integer ciudadId, @Param("desde") LocalDate desde);
}
