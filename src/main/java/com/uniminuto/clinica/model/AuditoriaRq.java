package com.uniminuto.clinica.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditoriaRq {
    private Long id;
    private LocalDateTime fechaHora;
    private String nombreUsuario;
    private String tablaAfectada;
    private Integer idRegistroAfectado;
    private String tipoEvento;
    private String valoresAntes;
    private String valoresDespues;
    private String descripcion;
    private String ipOrigen;
}

