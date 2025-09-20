package com.uniminuto.clinica.repository;


import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio JPA para operaciones de persistencia de la entidad Cita.
 * Proporciona consultas personalizadas para búsquedas por paciente y médico.
 *
 * @author crash
 * @version 1.0
 * @since 2025-09-20
 /* @see org.springframework.data.jpa.repository.JpaRepository
/ * @see Cita
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    /**
     * Busca todas las citas programadas para un paciente específico.
     *
     * Consulta SQL generada automáticamente:
     * SELECT * FROM cita WHERE paciente_id = ?
     *
    / * @param pacienteId Long identificador único del paciente
     * @return List<Cita> lista de citas del paciente ordenada por ID,
     *         puede ser vacía si el paciente no tiene citas
     /* @see Cita#getPaciente()
     /* @see Paciente#getId()
     */
    List<Cita> findByPacienteId(Long pacienteId);

    /**
     * Busca todas las citas asignadas a un médico específico.
     *
     * Útil para consultar la agenda médica y planificación de horarios.
     *
     * Consulta SQL generada automáticamente:
     * SELECT * FROM cita WHERE medico_id = ?
     *
     /* @param medicoId Long identificador único del médico
     * @return List<Cita> lista de citas asignadas al médico,
     *         puede ser vacía si el médico no tiene citas programadas
     /* @see Cita#getMedico()
     /* @see Medico#getId()
     */
    List<Cita> findByMedicoId(Long medicoId);

    /**
     * Busca citas similares dentro de una ventana de tiempo para validar duplicados.
     * Una cita se considera similar si tiene el mismo paciente, médico y motivo.
     *
     * @param pacienteId Long ID del paciente
     * @param medicoId Long ID del médico
     * @param motivo String motivo de la consulta (normalizado a minúsculas)
     * @param fechaInicio LocalDateTime límite inferior de búsqueda
     * @param fechaFin LocalDateTime límite superior de búsqueda
     * @return List<Cita> lista de citas similares en el rango de tiempo especificado
     */
    @Query("SELECT c FROM Cita c WHERE c.paciente.id = :pacienteId " +
            "AND c.medico.id = :medicoId " +
            "AND LOWER(c.motivo) = LOWER(:motivo) " +
            "AND c.fechaHora = :fechaHoraCita")
    List<Cita> findCitasIdenticas(@Param("pacienteId") Long pacienteId,
                                  @Param("medicoId") Long medicoId,
                                  @Param("motivo") String motivo,
                                  @Param("fechaHoraCita") LocalDateTime fechaHoraCita);

}
