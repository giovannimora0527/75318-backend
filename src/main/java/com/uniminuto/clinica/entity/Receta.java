package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "receta")
public class Receta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cita_id")
    private Cita cita;

    @Column(name = "medicamento_id")
    private Integer medicamentoId;

    @Column(name = "fecha_creacion_registro")
    private LocalDateTime fechaCreacionRegistro;

    @Column(name = "dosis")
    private String dosis;

    @Column(name = "indicaciones")
    private String indicaciones;
}
