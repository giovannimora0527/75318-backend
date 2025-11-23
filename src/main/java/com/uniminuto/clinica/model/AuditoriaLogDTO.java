package com.uniminuto.clinica.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaLogDTO {
    
    private Long id;
    private LocalDateTime fechaHora;

   
    @JsonProperty("usuarioUsername")
    private String username;

    private String tipoEvento;
    private String descripcion;
    
  
    @JsonProperty("ipAddress")
    private String ipAddress;
    
    private String resultado; 
}