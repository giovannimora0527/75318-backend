/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
<<<<<<< HEAD

=======
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
 */
package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
<<<<<<< HEAD
 * @author crash
 */
@Data
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
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
    private LocalDate fechaNacimiento;
    
    @Column(name = "genero")
    private char genero;

    @Column(name = "telefono")
    private String telefono;
    
    
    @Column(name = "direccion")
    private String direccion;
    
}
=======
 * @author darle
 */
@Data 
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {
    
    private static final long  serialVersionUID =1L; 
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    
    private long id ;
    

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "numero_documento", unique = true)
    private String numeroDocumento;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "genero")
    private String genero;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;
}
            
    

>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
