package com.uniminuto.clinica.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CitaRq {

    private Long idPaciente;
    private Long idMedico;
    private LocalDateTime fechaHora;
    private String estado;
    private String motivo;

}
