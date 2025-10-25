package com.uniminuto.clinica.model;

import lombok.Data;

/**
 * DTO de respuesta para especializaciones.
 */
@Data
public class EspecializacionRs {
    private Integer id;
    private String codigoEspecializacion;
    private String nombre;
    private String descripcion;
}
