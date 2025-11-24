package com.uniminuto.clinica.model;

import com.uniminuto.clinica.entity.Usuario;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaRs {
    private Integer status;
    private String mensaje;
    private String token;    // token para login
    private Usuario usuario; // usuario logueado

    // Constructor adicional para compatibilidad con 2 parámetros
    public RespuestaRs(Integer status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }
}
