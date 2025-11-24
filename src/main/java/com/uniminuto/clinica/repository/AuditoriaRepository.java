package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.AuditoriaSeguridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para gestionar registros de auditoría de seguridad.
 */
@Repository
public interface AuditoriaRepository extends JpaRepository<AuditoriaSeguridad, Long> {

    /**
     * Busca todos los intentos de recuperación de contraseña para un usuario específico.
     *
     * @param username Nombre de usuario
     * @param motivo   Motivo del registro (ej: RECUPERAR_CONTRASENA)
     * @return Lista de registros de auditoría
     */
    List<AuditoriaSeguridad> findByUsernameIngresadoAndMotivo(String username, String motivo);

    /**
     * Cuenta intentos fallidos de un usuario en un período específico.
     *
     * @param username    Nombre de usuario
     * @param motivo      Motivo del registro
     * @param fechaDesde  Fecha desde
     * @return Cantidad de intentos fallidos
     */
    @Query("SELECT COUNT(a) FROM AuditoriaSeguridad a " +
            "WHERE a.usernameIngresado = :username " +
            "AND a.motivo = :motivo " +
            "AND a.exitoso = false " +
            "AND a.fechaHora >= :fechaDesde")
    Long contarIntentosFallidos(
            @Param("username") String username,
            @Param("motivo") String motivo,
            @Param("fechaDesde") LocalDateTime fechaDesde
    );

    /**
     * Busca intentos de recuperación en las últimas 24 horas.
     *
     * @param username    Nombre de usuario
     * @param fechaDesde  Fecha desde (normalmente NOW() - 24 horas)
     * @return Lista de registros de auditoría
     */
    @Query("SELECT a FROM AuditoriaSeguridad a " +
            "WHERE a.usernameIngresado = :username " +
            "AND a.motivo = 'RECUPERAR_CONTRASENA' " +
            "AND a.fechaHora >= :fechaDesde " +
            "ORDER BY a.fechaHora DESC")
    List<AuditoriaSeguridad> findRecuperacionesRecientes(
            @Param("username") String username,
            @Param("fechaDesde") LocalDateTime fechaDesde
    );

    @Query("SELECT a FROM AuditoriaSeguridad a WHERE " +
            "(:username IS NULL OR a.usernameIngresado LIKE %:username%) AND " +
            "(:motivo IS NULL OR a.motivo = :motivo) AND " +
            "(:fechaDesde IS NULL OR a.fechaHora >= :fechaDesde) AND " +
            "(:fechaHasta IS NULL OR a.fechaHora <= :fechaHasta) " +
            "ORDER BY a.fechaHora DESC")
    List<AuditoriaSeguridad> findByFilters(
            @Param("username") String username,
            @Param("motivo") String motivo,
            @Param("fechaDesde") LocalDateTime fechaDesde,
            @Param("fechaHasta") LocalDateTime fechaHasta);
}
