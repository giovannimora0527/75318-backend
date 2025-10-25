package com.uniminuto.clinica.model;

import lombok.Data;

@Data
public class RespuestaRs {
    private String mensaje;
    private Integer status;

    public RespuestaRs() {}

    public RespuestaRs(Integer status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }
}
