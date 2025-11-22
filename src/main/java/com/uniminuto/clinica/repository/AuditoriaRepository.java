package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Auditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

    @Query("SELECT a FROM Auditoria a " +
            "WHERE (:usuarioFiltro IS NULL OR a.nombreUsuario LIKE %:usuarioFiltro%) " +
            "AND (:tipoEventoFiltro IS NULL OR a.tipoEvento LIKE %:tipoEventoFiltro%) " +
            "AND (:fechaFiltro IS NULL OR FUNCTION('DATE', a.fechaHora) = :fechaFiltro)")
    Page<Auditoria> buscarConFiltros(@Param("usuarioFiltro") String usuarioFiltro,
                                     @Param("tipoEventoFiltro") String tipoEventoFiltro,
                                     @Param("fechaFiltro") String fechaFiltro,
                                     Pageable pageable);
}

