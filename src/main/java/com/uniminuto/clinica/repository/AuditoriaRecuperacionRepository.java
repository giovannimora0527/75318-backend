package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.AuditoriaRecuperacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRecuperacionRepository extends JpaRepository<AuditoriaRecuperacion, Long> {
	// Consulta con filtros opcionales y paginación
	@org.springframework.data.jpa.repository.Query("SELECT a FROM AuditoriaRecuperacion a WHERE (:username IS NULL OR a.username LIKE %:username%) AND (:descripcion IS NULL OR a.descripcion LIKE %:descripcion%) AND (:start IS NULL OR a.fecha >= :start) AND (:end IS NULL OR a.fecha <= :end)")
	org.springframework.data.domain.Page<AuditoriaRecuperacion> buscar(
		@org.springframework.data.repository.query.Param("username") String username,
		@org.springframework.data.repository.query.Param("descripcion") String descripcion,
		@org.springframework.data.repository.query.Param("start") java.time.LocalDateTime start,
		@org.springframework.data.repository.query.Param("end") java.time.LocalDateTime end,
		org.springframework.data.domain.Pageable pageable
	);
}
