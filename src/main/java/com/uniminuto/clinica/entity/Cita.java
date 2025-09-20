package com.uniminuto.clinica.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cita")

public class Cita implements Serializable {
    private static final long serialVersionUID = 1L;

    // Define fields, constructors, getters, and setters here
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "estado")
    private String estado;
}
