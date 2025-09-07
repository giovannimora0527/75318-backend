package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="paciente")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="usuario_id")
    private Long usuarioId;

    @Column(name="tipo_documento")
    private String tipoDocumento;

    @Column(name="numero_documento")
    private String numeroDocumento;

    private String nombres;
    private String apellidos;

    @Column(name="fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private String genero;
    private String telefono;
    private String direccion;
    private Integer edad;
}
