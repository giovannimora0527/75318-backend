package com.uniminuto.clinica.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_login")
public class AuditoriaLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;
    private LocalDateTime fechaHora;
    private String ip;
    private boolean exito;
    private String motivo;

    public AuditoriaLogin() {}

    public AuditoriaLogin(String usuario, LocalDateTime fechaHora, String ip, boolean exito, String motivo) {
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        this.ip = ip;
        this.exito = exito;
        this.motivo = motivo;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
