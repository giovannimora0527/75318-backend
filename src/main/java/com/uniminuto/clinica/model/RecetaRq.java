package com.uniminuto.clinica.model;

import lombok.Data;

/**
 *
 * @author JAVIER-CUERVO
 */
@Data
public class RecetaRq {
    private String citaId;
    private String medicamentoId;
    private String dosis;
    private String indicaciones;
}