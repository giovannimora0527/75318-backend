/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.model;

/**
 *
 * @author Usuario
 */
public class CitaRs {
    private Long id;
    private String fechaHora;
    private String estado;
    private String motivo;
    private String nombrePaciente;
    private String nombreMedico;
    private Long pacienteId;
    private Long medicoId;
    
    public Long getPacienteId() 
    { return pacienteId; }
    
    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId; 
    }
    
    public Long getMedicoId() { 
        return medicoId; 
    }
    
    public void setMedicoId(Long medicoId) { 
        this.medicoId = medicoId; 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }
}