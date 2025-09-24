package com.uniminuto.clinica.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entidad que representa un paciente en el sistema.
 * Basada en la tabla 'paciente'.
 */
@Entity
@Table(name = "paciente")
@Data
public class Paciente implements Serializable {
    /**
     * Identificador único del paciente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identificador del usuario asociado al paciente.
     */
    @Column(name = "usuario_id")
    private Integer usuarioId;

    /**
     * Tipo de documento del paciente (obligatorio).
     */
    @Column(name = "tipo_documento", nullable = false, length = 10)
    private String tipoDocumento;

    /**
     * Número de documento del paciente (obligatorio, único).
     */
    @Column(name = "numero_documento", nullable = false, length = 20, unique = true)
    private String numeroDocumento;

    /**
     * Nombres del paciente (obligatorio).
     */
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    /**
     * Apellidos del paciente (obligatorio).
     */
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    /**
     * Fecha de nacimiento del paciente (obligatorio).
     */
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    /**
     * Género del paciente.
     */
    @Column(name = "genero", length = 1)
    private String genero;

    /**
     * Teléfono del paciente.
     */
    @Column(name = "telefono", length = 20)
    private String telefono;

    /**
     * Dirección del paciente.
     */
    @Column(name = "direccion")
    private String direccion;
}
