package com.uniminuto.clinica.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Entidad que representa una receta médica en el sistema.
 * Basada en la tabla 'receta'.
 */
@Entity
@Table(name = "receta")
@Data
public class Receta implements Serializable {
    /**
     * Identificador único de la receta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identificador de la cita asociada (obligatorio).
     */
    @ManyToOne
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    /**
     * Identificador del medicamento (obligatorio).
     */
    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    /**
     * Dosis prescrita (obligatorio).
     */
    @Column(name = "dosis", nullable = false)
    private String dosis;

    /**
     * Indicaciones adicionales.
     */
    @Column(name = "indicaciones")
    private String indicaciones;

    /**
     * Fecha de creación del registro.
     */
    @Column(name = "fecha_creacion_registro")
    private LocalDateTime fechaCreacionRegistro;
}
