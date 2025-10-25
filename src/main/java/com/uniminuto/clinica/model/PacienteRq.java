package com.uniminuto.clinica.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PacienteRq {

    private Integer id;

    private Integer usuario_Id;

    @NotBlank(message = "El tipo de ducumento es obligatorio")
    private String tipoDocumento;

    @NotBlank(message = "El nuemero de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;

    @NotNull(message = "El apellido es obligatoria")
    private String apellidos;

    @NotBlank(message = "La fecha de nacimiento es obligatoria")
    private String fechaNacimiento;

    @NotBlank(message = "El genero es obligatorio")
    private String genero;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "La direccion es obligatorio")
    private String direccion;



}
