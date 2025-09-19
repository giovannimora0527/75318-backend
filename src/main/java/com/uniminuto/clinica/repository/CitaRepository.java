package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jartunduaga
 */

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
}
