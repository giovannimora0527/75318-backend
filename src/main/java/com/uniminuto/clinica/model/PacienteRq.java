package com.uniminuto.clinica.model;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * @author jartunduaga
 */
@Data
public class PacienteRq {

    @NotNull(message = "El usuario ID es requerido")
    private Integer usuarioId;

    @NotBlank(message = "El tipo de documento es requerido")
    @Size(max = 10, message = "El tipo de documento no puede exceder 10 caracteres")
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es requerido")
    @Size(max = 20, message = "El número de documento no puede exceder 20 caracteres")
    private String numeroDocumento;

    @NotBlank(message = "Los nombres son requeridos")
    @Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son requeridos")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    private String apellidos;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El género debe ser M o F")
    private String genero;

    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String telefono;

    private String direccion;

    @Email(message = "El formato del email no es válido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    private String email;
}