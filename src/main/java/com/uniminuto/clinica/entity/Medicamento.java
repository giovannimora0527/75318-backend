package com.uniminuto.clinica.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Entidad que representa un medicamento en el sistema.
 * Basada en la tabla 'medicamento'.
 */
@Entity
@Table(name = "medicamento")
@Data
public class Medicamento implements Serializable {
    /**
     * Identificador único del medicamento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Nombre del medicamento (obligatorio, único).
     */
    @Column(name = "nombre", nullable = false, length = 100, unique = true)
    private String nombre;

    /**
     * Descripción del medicamento.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Presentación del medicamento.
     */
    @Column(name = "presentacion", length = 100)
    private String presentacion;

    /**
     * Fecha de compra del medicamento (obligatorio).
     */
    @Column(name = "fecha_compra", nullable = false)
    private LocalDate fechaCompra;

    /**
     * Fecha de vencimiento del medicamento (obligatorio).
     */
    @Column(name = "fecha_vence", nullable = false)
    private LocalDate fechaVence;

    /**
     * Fecha de creación del registro (obligatorio).
     */
    @Column(name = "fecha_creacion_registro", nullable = false)
    private LocalDateTime fechaCreacionRegistro;

    /**
     * Fecha de modificación del registro.
     */
    @Column(name = "fecha_modificacion_registro")
    private LocalDateTime fechaModificacionRegistro;
}

