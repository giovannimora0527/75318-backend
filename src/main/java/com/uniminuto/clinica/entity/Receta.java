package com.uniminuto.clinica.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
/**
 *
 * @author jartunduaga
 */

@Data
@Entity
@Table(name = "receta")

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
}
