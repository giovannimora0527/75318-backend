package com.uniminuto.clinica.model;

import lombok.Data;
import java.sql.Date;
import java.sql.Time;

@Data
public class CitaRq {
    private Long pacienteId;
    private Long medicoId;
    private Date fechaHora;
    private String estado;
    private String motivo;
}