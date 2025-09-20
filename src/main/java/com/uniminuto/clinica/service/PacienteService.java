/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author crash
 */
/*
Interfaz de servicio para la gestión de pacientes en el sistema clínico.
Proporciona operaciones CRUD y consultas específicas para la entidad Paciente.
 * @author crash
 * @version 1.0
 * @since 2025-09-20
* */
public interface PacienteService {
    //Lista todos los pacientes del sistema sin aplicar ningún orden específico.
    //@return List<Paciente> Lista completa de pacientes registrados en el sistema
    List<Paciente> listarTodo();

    //Funcion buscar por documento identidad
    Paciente encontrarPorDocumentoIdentidad(String numeroDocumento) throws BadRequestException;

    /*
    Lista todos los pacientes ordenados por fecha de nacimiento de manera ascendente.
    * @return List<Paciente> Lista de pacientes ordenada cronológicamente por fecha de nacimiento
     * @throws RuntimeException si ocurre un error al acceder a la base de datos
     * @see java.time.LocalDate#compareTo(java.time.chrono.ChronoLocalDate)
     */
    List<Paciente> listarPacientesDes();

    /**
     * Busca un paciente específico mediante su identificador único.
     *
     * @param id Long identificador único del paciente en la base de datos
     * @return Paciente objeto que corresponde al ID proporcionado
     * @throws BadRequestException si no existe un paciente con el ID especificado
     * @throws IllegalArgumentException si el id es null
     */
    Paciente buscarPacienteId(Long id) throws BadRequestException;
}
