<<<<<<< HEAD
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
>>>>>>> origin/916724_BrayanEscorcha
package com.uniminuto.clinica.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
<<<<<<< HEAD

import lombok.Data;

@Data
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    /**
     * Id serializable.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;

    @Column(name = "genero")
    private String genero;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;




=======
import lombok.Data;

/**
 *
 * @author Brayan Escorcha
 */
@Data
@Entity
@Table(name="paciente")
public class Paciente implements Serializable {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long Id;
    
    @Column(name = "nombre",nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;
    
    @Column(name = "documento", unique = true, nullable = false )
    private String documento;
    
    @Column(name = "edad")
    private Integer edad ;
>>>>>>> origin/916724_BrayanEscorcha
}
