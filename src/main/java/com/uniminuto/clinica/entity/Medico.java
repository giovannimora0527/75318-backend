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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
<<<<<<< HEAD
<<<<<<< HEAD
 * @author crash
=======
 * @author lmora
>>>>>>> gmora
=======
 * @author lmora
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
 */
@Data
@Entity
@Table(name = "medico")
<<<<<<< HEAD
public class Medico implements Serializable{
    private static final long serialVersionUID = 1L;
    
=======
public class Medico implements Serializable {

    /**
     * Id serializable.
     */
    private static final long serialVersionUID = 1L;
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    
    @Column(name = "numero_documento")
    private String documento;
    
    @Column(name = "nombres")
    private String nombres;
    
    @Column(name = "apellidos")
    private String apellidos;
    
    @Column(name = "telefono")
    private String telefono;
    
    @Column(name = "registro_profesional")
    private String registroProfesional;
    
    @ManyToOne
    @JoinColumn(name = "especializacion_id")
    private Especializacion especializacion;

}
