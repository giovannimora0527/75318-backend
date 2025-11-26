package com.uniminuto.clinica.model;

import javax.validation.constraints.NotBlank;

public class RecuperarPasswordRequest {
    @NotBlank(message = "El username es obligatorio.")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

