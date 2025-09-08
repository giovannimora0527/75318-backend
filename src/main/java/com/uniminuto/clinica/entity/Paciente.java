package com.uniminuto.clinica.entity;

import javax.persistence.*;
import lombok.Data;
import java.sql.Date; 

@Data
@Entity
@Table(name = "paciente") 
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "tipo_documento", nullable = false, length = 10)
    private String tipoDocumento;

    @Column(name = "numero_documento", nullable = false, unique = true, length = 20)
    private String numeroDocumento;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    @Column(name = "genero", length = 1)
    private Character genero;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Lob 
    @Column(name = "direccion")
    private String direccion;
}