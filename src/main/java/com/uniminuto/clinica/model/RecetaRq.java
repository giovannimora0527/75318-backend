package com.uniminuto.clinica.model;

import lombok.Data;

/**
 * Modelo de datos de transferencia (DTO) que encapsula la información
 * necesaria para crear una nueva receta médica en el sistema.
 *
 * Este modelo se utiliza en las operaciones de API REST para recibir
 * datos del cliente relacionados con prescripciones médicas.
 *
 * @author crash
 * @version 1.0
 * @since 2025-09-20
 /* @see Receta
 /* @see RecetaService#guardarReceta(RecetaRq)
 */
@Data
public class RecetaRq {
    private Long idCita;//@param idCita Long clave foránea hacia la entidad Cita
    private Integer medicamentoId;//@param medicamentoId Integer identificador del medicamento en el sistema
    private String dosis;//@param dosis String descripción de la dosis prescrita
    private String indicaciones;//@param indicaciones String instrucciones adicionales para el paciente
}
