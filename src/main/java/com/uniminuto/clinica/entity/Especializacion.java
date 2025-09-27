<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

=======
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
package com.uniminuto.clinica.entity;

import java.io.Serializable;
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
=======
 * @author lmora
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
 */
@Data
@Entity
@Table(name = "especializacion")
<<<<<<< HEAD
public class Especializacion implements Serializable{
=======
public class Especializacion implements Serializable {
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "codigo_especializacion")
    private String codigoEspecializacion;
}
