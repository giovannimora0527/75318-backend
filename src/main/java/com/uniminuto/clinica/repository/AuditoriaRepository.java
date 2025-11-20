package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Auditoria.
 * 
 * @author Sistema
 */
@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    
    List<Auditoria> findAllByOrderByFechaHoraAsc();
    
    List<Auditoria> findByTipoEventoOrderByFechaHoraAsc(String tipoEvento);
}

