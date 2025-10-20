/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.model;

import lombok.Data;
import java.time.LocalDate;
/**
 *
 * @author Usuario
 */

/**
 * DTO para crear o actualizar un paciente.
 */
@Data
public class PacienteRq {
    private Long id;
    private Long usuarioId;       
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String genero;        
    private String telefono;
    private String direccion;
}
