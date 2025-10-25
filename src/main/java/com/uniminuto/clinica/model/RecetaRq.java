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
@Data
public class RecetaRq {
    private Long id;
    private Long citaId;
    private Long medicamentoId;
    private String dosis;
    private String indicaciones;
}