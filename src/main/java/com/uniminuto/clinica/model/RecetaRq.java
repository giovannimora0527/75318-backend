package com.uniminuto.clinica.model;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
 * Clase que representa la solicitud para crear o actualizar una receta médica.
 */
@Data
public class RecetaRq {

    /**
     * Id de la receta.
     */
    private Long id;

    /**
     * Identificador de la cita asociada a la receta.
     */
    @NotNull(message = "El identificador de la cita es obligatorio.")
    private Long citaId;

    /**
     * Identificador del medicamento recetado.
     */
    @NotNull(message = "El identificador del medicamento es obligatorio.")
    private Long medicamentoId;

    /**
     * Dosis prescrita del medicamento.
     */
    @NotBlank(message = "La dosis es obligatoria.")
    private String dosis;

    /**
     * Indicaciones adicionales para el uso del medicamento.
     */
    @NotBlank(message = "Las indicaciones son obligatorias.")
    private String indicaciones;
}
