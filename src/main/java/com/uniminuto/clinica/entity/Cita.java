package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "cita")
public class Cita implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_hora")
    private LocalDate fechaHora;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;
}
