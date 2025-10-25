/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.model;

import lombok.Data;

/**
 *
 * @author Usuario
 */

/**
 * DTO para crear o actualizar un medicamento.
 */
@Data
public class MedicamentoRq {
    private Long id;
    private String nombre;
    private String descripcion;
    private String presentacion;
}