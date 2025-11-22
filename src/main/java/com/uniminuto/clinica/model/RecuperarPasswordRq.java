package com.uniminuto.clinica.model;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * Request para recuperación de contraseña.
 */
@Data
public class RecuperarPasswordRq {

    /**
     * Nombre de usuario para recuperar contraseña.
     */
    @NotBlank(message = "El nombre de usuario es obligatorio.")
    private String username;
}
