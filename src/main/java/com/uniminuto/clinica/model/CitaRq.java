package com.uniminuto.clinica.model;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CitaRq {

    @NotNull(message = "El campo pacienteId es obligatorio")
    private Long pacienteId;

    @NotNull(message = "El campo medicoId es obligatorio")
    private Long medicoId;

    @NotBlank(message = "El campo fechaHora es obligatorio")
    private String fechaHora;

    @NotBlank(message = "El campo estado es obligatorio")
    private String estado;

    @NotBlank(message = "El campo motivo es obligatorio")
    private String motivo;
}
