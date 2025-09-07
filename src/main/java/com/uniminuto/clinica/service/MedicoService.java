/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Medico;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author crash
 */
public interface MedicoService {
    List<Medico> listarMedicos();
    
    List<Medico> buscarPorEspecialidad(String codigo)
            throws BadRequestException;
}
