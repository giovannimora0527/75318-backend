package com.uniminuto.clinica.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UsuarioRq {
    
    private String username;
    
    
    @JsonProperty("password")
    private String pass;
    
    private String rol;
}