package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author JAVIER-CUERVO
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByPacienteId(Long pacienteId);
    List<Cita> findByMedicoId(Long medicoId);
    List<Cita> findAllByOrderByFechaHoraDesc();
}