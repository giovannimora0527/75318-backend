package com.uniminuto.clinica.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "auditoria")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ✅ Clave primaria obligatoria

    @Column(name = "usuario", nullable = false)
    private String usuario;

    @Column(name = "accion", nullable = false)
    private String accion;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "ip")
    private String ip;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;
}
