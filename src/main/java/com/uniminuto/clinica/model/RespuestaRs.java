package com.uniminuto.clinica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @author lmora
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaRs<T> {
    private String mensaje;   
    private Integer status;
    private T data;
}
