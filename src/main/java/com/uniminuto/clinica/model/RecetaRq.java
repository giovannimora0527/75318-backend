package com.uniminuto.clinica.model;

import lombok.Data;

@Data
public class RecetaRq {
    private Long idCita;
    private Integer medicamentoId;
    private String dosis;
    private String indicaciones;
}
