package com.uniminuto.clinica.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "auditoria_recuperacion")
public class AuditoriaRecuperacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "username_intentado")
    private String usernameIntentado;

    @Column(name = "descripcion")
    private String descripcion;
}
