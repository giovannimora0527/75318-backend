package com.uniminuto.clinica.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PacienteRq {
    private Long id;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;

}
