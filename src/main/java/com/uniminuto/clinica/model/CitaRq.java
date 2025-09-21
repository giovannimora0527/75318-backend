package com.uniminuto.clinica.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Modelo de datos de transferencia (DTO) que encapsula la información
 * necesaria para crear una nueva cita médica en el sistema.
 *
 * Este modelo se utiliza en las operaciones de API REST para recibir
 * datos del cliente y validarlos antes de la persistencia.
 *
 * @author crash
 * @version 1.0
 * @since 2025-09-20
 /* @see Cita
 /* @see CitaService#guardarCita(CitaRq)
 */

@Data
public class CitaRq {

    private Long idPaciente; //idPaciente Long clave foránea hacia la entidad Paciente
    private Long idMedico; //idMedico Long clave foránea hacia la entidad Medico
    private LocalDateTime fechaHora; // fechaHora LocalDateTime fecha y hora de la cita
    private String estado;// estado String estado de la cita (programada, cancelada, completada)
    private String motivo; // motivo String motivo de la cita

}
