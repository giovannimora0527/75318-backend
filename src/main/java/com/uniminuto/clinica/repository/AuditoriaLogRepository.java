package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.AuditoriaLog;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaLogRepository extends JpaRepository<AuditoriaLog, Long> {
    
    Page<AuditoriaLog> findAllByOrderByFechaHoraDesc(Pageable pageable);
    
    @Query("SELECT a FROM AuditoriaLog a WHERE " +
            "(:username IS NULL OR a.usuarioUsername = :username) AND " +
            "(:tipoEvento IS NULL OR a.tipoEvento = :tipoEvento) AND " +
            "(:fechaInicio IS NULL OR a.fechaHora >= :fechaInicio) AND " +
            "(:fechaFin IS NULL OR a.fechaHora <= :fechaFin) " +
            "ORDER BY a.fechaHora DESC")
    Page<AuditoriaLog> buscarConFiltros(
            @Param("username") String username,
            @Param("tipoEvento") String tipoEvento,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            Pageable pageable);
}