package com.uniminuto.clinica.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

/**
 * Entidad que representa a un médico en el sistema.
 *
 * @author lmora
 */
@Data
@Entity
@Table(name = "medico")
public class Medico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "tipo_documento", nullable = false, length = 10)
    private String tipoDocumento;

    @Column(name = "numero_documento", nullable = false, unique = true, length = 20)
    private String numeroDocumento;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "registro_profesional", nullable = false, unique = true, length = 50)
    private String registroProfesional;

    @ManyToOne(optional = false)
    @JoinColumn(name = "especializacion_id", nullable = false)
    private Especializacion especializacion;
}
