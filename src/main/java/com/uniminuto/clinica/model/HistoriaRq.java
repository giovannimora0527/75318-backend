package com.uniminuto.clinica.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class HistoriaRq {
    private Integer id;

    @NotNull(message = "El identificador del paciente es obligatorio.")
    private Integer paciente;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @NotBlank(message = "La fecha es obligatoria")
    private LocalDate fecha;
}
