package com.uniminuto.clinica.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author jartunduaga
 */

@Data
@Entity
@Table(name = "receta")

/**
 * Para el punto 5 se procedió a modificar la tabla con la findlaida de agregar el campo de fecha de creación
 * ALTER TABLE receta
 * ADD COLUMN fecha_creacion_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
 */

public class Receta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Cita
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    @Column(name = "medicamento_id", nullable = false)
    private Long medicamentoId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String dosis;

    @Column(columnDefinition = "TEXT")
    private String indicaciones;

    @Column(name = "fecha_creacion_registro", nullable = false, updatable = false)
    private LocalDateTime fechaCreacionRegistro;


    //Agrengando el PrePersist no es necesario pedirlo en el Body de la request ya que este dato siempre estará presente
    @PrePersist
    public void prePersist() {
        this.fechaCreacionRegistro = LocalDateTime.now();
    }
}