package com.uniminuto.clinica.model;

import lombok.Data;
import java.sql.Time;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

@Data
public class CitaRq {
     @NotNull
    private Long pacienteId;
    @NotNull
    private Long medicoId;
    @NotNull
    private LocalDateTime fechaHora;
    @NotNull
    private String estado;
    private String motivo; 
}