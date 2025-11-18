package com.uniminuto.clinica.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jartunduaga
 */

@Data
@Entity
@Table(name = "receta")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

/**
 * Para el punto 5 se procedió a modificar la tabla con la findlaida de agregar el campo de fecha de creación
 * ALTER TABLE receta
 * ADD COLUMN fecha_creacion_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
 */

public class Receta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Cita
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cita_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cita cita;

    @Column(name = "medicamento_id", nullable = false)
    private Long medicamentoId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String dosis;

    @Column(columnDefinition = "TEXT")
    private String indicaciones;

    @Column(name = "fecha_creacion_registro", nullable = false, updatable = false)
    private LocalDateTime fechaCreacionRegistro;

    // Campo transitorio para almacenar el medicamento cargado
    @Transient
    private Medicamento medicamentoCargado;

    //Agrengando el PrePersist no es necesario pedirlo en el Body de la request ya que este dato siempre estará presente
    @PrePersist
    public void prePersist() {
        this.fechaCreacionRegistro = LocalDateTime.now();
    }

    /**
     * Establece el medicamento cargado (para uso en el servicio)
     */
    public void setMedicamentoCargado(Medicamento medicamento) {
        this.medicamentoCargado = medicamento;
    }

    // Métodos getter para compatibilidad con el frontend
    
    /**
     * Obtiene la fecha de la cita (para el frontend)
     */
    @com.fasterxml.jackson.annotation.JsonGetter("fecha")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime getFecha() {
        return cita != null ? cita.getFechaHora() : null;
    }

    /**
     * Obtiene el diagnóstico (motivo de la cita)
     */
    @com.fasterxml.jackson.annotation.JsonGetter("diagnostico")
    public String getDiagnostico() {
        return cita != null ? cita.getMotivo() : null;
    }

    /**
     * Obtiene el paciente desde la cita
     */
    @com.fasterxml.jackson.annotation.JsonGetter("paciente")
    public Paciente getPaciente() {
        return cita != null ? cita.getPaciente() : null;
    }

    /**
     * Obtiene el médico desde la cita
     */
    @com.fasterxml.jackson.annotation.JsonGetter("medico")
    public Medico getMedico() {
        return cita != null ? cita.getMedico() : null;
    }

    /**
     * Obtiene el ID de la cita
     */
    @com.fasterxml.jackson.annotation.JsonGetter("citaId")
    public Long getCitaId() {
        return cita != null ? cita.getId() : null;
    }

    /**
     * Obtiene la fecha de creación formateada
     */
    @com.fasterxml.jackson.annotation.JsonGetter("fechaCreacion")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime getFechaCreacion() {
        return fechaCreacionRegistro;
    }

    /**
     * Obtiene los medicamentos como lista (para compatibilidad con frontend)
     * Nota: Solo hay un medicamentoId, pero el frontend espera un array
     */
    @com.fasterxml.jackson.annotation.JsonGetter("medicamentos")
    @Transient
    public List<Medicamento> getMedicamentos() {
        List<Medicamento> medicamentos = new ArrayList<>();
        if (medicamentoCargado != null) {
            medicamentos.add(medicamentoCargado);
        }
        return medicamentos;
    }

    /**
     * Campo activo siempre true (para compatibilidad con frontend)
     */
    @com.fasterxml.jackson.annotation.JsonGetter("activo")
    @Transient
    public Boolean getActivo() {
        return true;
    }

    /**
     * Campo observaciones (vacío por defecto, para compatibilidad con frontend)
     */
    @com.fasterxml.jackson.annotation.JsonGetter("observaciones")
    @Transient
    public String getObservaciones() {
        return "";
    }
}