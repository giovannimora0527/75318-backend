package com.uniminuto.clinica.entity;

import javax.persistence.*;
import lombok.Data;
import java.sql.Date;
import java.sql.Time;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore; // Importa esta clase

@Data
@Entity
@Table(name = "cita")
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore // Ignora este campo al serializar a JSON
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @JsonIgnore // Ignora este campo al serializar a JSON
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(name = "fecha_hora", nullable = false)
    private Date fechaHora;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Lob
    @Column(name = "motivo")
    private String motivo;
}