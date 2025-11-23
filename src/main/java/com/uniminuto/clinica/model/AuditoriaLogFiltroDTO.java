package com.uniminuto.clinica.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AuditoriaLogFiltroDTO {
    private String username;
    private String tipoEvento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer pagina;
    private Integer tamanoPagina;
}