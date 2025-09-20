package com.uniminuto.clinica.model;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RecetaRq implements Serializable {

    @NotNull(message = "El campo 'citaId' es obligatorio.")
    private Long citaId;

    @NotNull(message = "El campo 'medicamentoId' es obligatorio.")
    private Long medicamentoId;

    @NotNull(message = "El campo 'dosis' es obligatorio.")
    private String dosis;

    private String indicaciones; // 'indicaciones' no es obligatorio en la base de datos
}