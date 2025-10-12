package com.uniminuto.clinica.model;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class HistoriaMedicaRq {

    @NotNull(message = "El paciente es obligatorio")
    private Long pacienteId;  // usamos camelCase y mapeamos en el controlador

    private LocalDateTime fechaCreacion; // opcional, si no se envía se usa LocalDateTime.now()
}
