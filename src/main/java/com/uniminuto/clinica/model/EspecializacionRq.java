package com.uniminuto.clinica.model;

import lombok.Data;

/**
 * DTO para crear/actualizar especializaciones.
 */
@Data
public class EspecializacionRq {
    private Integer id;
    private String codigoEspecializacion;
    private String nombre;
    private String descripcion;
}
