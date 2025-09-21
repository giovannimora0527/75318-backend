package com.uniminuto.clinica.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
    
/**
 *
 * @author JAVIER-CUERVO
 */

@Data
@Entity
@Table(name="receta")
public class Receta implements Serializable {

    /**
     * Id serializable.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cita_id")
    private String citaId;

    @Column(name = "medicamento_id")
    private String medicamentoId;

    @Column(name = "dosis")
    private String dosis;

    @Column(name = "indicaciones")
    private String indicaciones;

    @Column(name = "fecha_creacion_registro")
    private LocalDateTime fechaCreacionRegistro;
}