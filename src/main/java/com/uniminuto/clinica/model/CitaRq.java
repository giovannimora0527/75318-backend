package com.uniminuto.clinica.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaRq {

    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime fechaHora;
    private String motivo;
}
