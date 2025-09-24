package com.uniminuto.clinica.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * Entidad que representa una cita médica en el sistema.
 * Basada en la tabla 'cita'.
 */
@Entity
@Table(name = "cita")
@Data
public class Cita implements Serializable {
    /**
     * Identificador único de la cita.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identificador del paciente (obligatorio).
     */
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    /**
     * Identificador del médico (obligatorio).
     */
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    /**
     * Fecha y hora de la cita (obligatorio).
     */
    @Column(name = "fecha_hora", nullable = false)
    private LocalDate fechaHora;

    /**
     * Estado de la cita (obligatorio).
     */
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    /**
     * Motivo de la cita.
     */
    @Column(name = "motivo")
    private String motivo;
}
