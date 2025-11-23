package com.uniminuto.clinica.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Boolean exitoso;
    private String mensaje;
    private String token;
    private String username;
    private String rol;
    private Boolean bloqueado;
    private Integer intentosRestantes;
}