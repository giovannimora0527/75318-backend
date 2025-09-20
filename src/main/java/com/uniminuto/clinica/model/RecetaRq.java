package com.uniminuto.clinica.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RecetaRq {
    private Long citaId;
    private Long medicamentoId;
    private String dosis;
    private String indicaciones;
}
