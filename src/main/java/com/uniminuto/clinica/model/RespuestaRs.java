package com.uniminuto.clinica.model;

import com.uniminuto.clinica.entity.Cita;
import lombok.Data;


/**
 *
 * @author lmora
 */
@Data
public class RespuestaRs{
    private String mensaje;   
    private Integer status;
}
