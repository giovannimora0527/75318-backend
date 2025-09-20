package com.uniminuto.clinica.service;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

import java.util.List;
/*
 * Interfaz de servicio para la gestión de citas médicas en el sistema clínico.
 * Maneja las operaciones de consulta, creación y administración de citas
 * entre pacientes y médicos.
 * @author crash
 * @version 1.0
 * @since 2025-09-20
* */
public interface CitaService {
    /**
     * Busca todas las citas asociadas a un paciente específico.
     *
     * @param id Long identificador único del paciente
     * @return List<Cita> Lista de citas programadas o realizadas por el paciente
     * @throws BadRequestException si el ID del paciente no existe en el sistema
     */
    List<Cita> buscarPorPaciente(Long id)
            throws BadRequestException;

    /**
     * Busca todas las citas asociadas a un médico específico.
     *
     * @param id Long identificador único del médico
     * @return List<Cita> Lista de citas asignadas al médico especificado
     * @throws BadRequestException si el ID del médico no existe en el sistema
     */
    List<Cita> buscarPorMedico(Long id)
            throws BadRequestException;

    /**
     * Crea y persiste una nueva cita médica en el sistema.
     * Valida la existencia del paciente y médico antes de crear la cita.
     *
     * @param CitaNuevo CitaRq objeto que contiene los datos de la nueva cita
     * @return RespuestaRs objeto que indica el resultado de la operación
     * @throws BadRequestException si los datos de entrada son inválidos o incompletos
     */
    RespuestaRs guardarCita(CitaRq CitaNuevo) throws BadRequestException;

    /**
     * Lista todas las citas del sistema ordenadas por fecha y hora descendente.
     * Las citas más recientes aparecen primero en la lista.
     *
     * @return List<Cita> Lista de citas ordenada cronológicamente (más recientes primero)
     * @throws RuntimeException si ocurre un error al acceder a la base de datos
     */
    List<Cita> listarCitasDesc();

    Cita buscarCitaPorId(Long id) throws BadRequestException;
}

