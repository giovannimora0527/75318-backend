package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para la entidad Cita.
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {


    List<Cita> findAllByOrderByFechaHoraDesc();

    List<Cita> findByMedicoAndFechaHoraBetween(
            Medico medicoId, LocalDateTime fechaIni, LocalDateTime fechaFin);

    List<Cita> findByPacienteAndFechaHoraBetween(
            Paciente pacienteId, LocalDateTime fechaIni, LocalDateTime fechaFin);


}
