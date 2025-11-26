package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "rol", nullable = false)
    private String rol;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "email", nullable = false)
    private String email;

    // Campos para control de login
    @Column(name = "intentos_fallidos")
    private int intentosFallidos = 0;

    @Column(name = "bloqueado_hasta")
    private Boolean bloqueado;


    @Column(name = "fecha_bloqueo")
    private LocalDateTime fechaBloqueo;

    @Column(name = "fecha_desbloqueo")
    private LocalDateTime fechaDesbloqueo;

    @Transient
    private String token;
}
