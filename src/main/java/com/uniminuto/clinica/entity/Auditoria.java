package com.uniminuto.clinica.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora;

    private String nombreUsuario;

    private String tablaAfectada;

    private Integer idRegistroAfectado;

    private String tipoEvento; // INSERT, UPDATE, DELETE, LOGIN...

    @Column(columnDefinition = "TEXT")
    private String valoresAntes;

    @Column(columnDefinition = "TEXT")
    private String valoresDespues;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String ipOrigen;

    // Getters y Setters

    public String getIpOrigen() {
        return ipOrigen;
    }

    public void setIpOrigen(String ipOrigen) {
        this.ipOrigen = ipOrigen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValoresDespues() {
        return valoresDespues;
    }

    public void setValoresDespues(String valoresDespues) {
        this.valoresDespues = valoresDespues;
    }

    public String getValoresAntes() {
        return valoresAntes;
    }

    public void setValoresAntes(String valoresAntes) {
        this.valoresAntes = valoresAntes;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Integer getIdRegistroAfectado() {
        return idRegistroAfectado;
    }

    public void setIdRegistroAfectado(Integer idRegistroAfectado) {
        this.idRegistroAfectado = idRegistroAfectado;
    }

    public String getTablaAfectada() {
        return tablaAfectada;
    }

    public void setTablaAfectada(String tablaAfectada) {
        this.tablaAfectada = tablaAfectada;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
