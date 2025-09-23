/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.model;

import lombok.Data;
import java.time.LocalDateTime;
/**
 *
 * @author Usuario
 */

/**
 * DTO para crear o actualizar una cita.
 */
@Data
public class CitaRq {
    private Long id;         
    private Long pacienteId;  
    private Long medicoId;    
    private LocalDateTime fechaHora;
    private String estado;    
    private String motivo;    
}
