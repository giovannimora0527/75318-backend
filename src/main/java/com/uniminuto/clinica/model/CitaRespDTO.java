package com.uniminuto.clinica.model;

import java.time.LocalDateTime;

public class CitaRespDTO {

    private Long id;
    private String pacienteNombre;
    private String medicoNombre;
    private String motivo;
    private LocalDateTime fechaHora;
    private String estado;

    public CitaRespDTO(Long id, String pacienteNombre, String medicoNombre,
                       String motivo, LocalDateTime fechaHora, String estado) {
        this.id = id;
        this.pacienteNombre = pacienteNombre;
        this.medicoNombre = medicoNombre;
        this.motivo = motivo;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    // Getters
    public Long getId() { return id; }
    public String getPacienteNombre() { return pacienteNombre; }
    public String getMedicoNombre() { return medicoNombre; }
    public String getMotivo() { return motivo; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getEstado() { return estado; }
}
