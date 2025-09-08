/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author crash
 */
@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository PacienteRepository;

    @Override
    public List<Paciente> listarTodo() {
        return this.PacienteRepository.findAll();
    }
    //Funcion buscar por documento identidad
    @Override
    public Paciente encontrarPorDocumentoIdentidad(String numeroDocumento)
            throws BadRequestException {
        Optional<Paciente> optUser = this.PacienteRepository
                .findByNumeroDocumento(numeroDocumento);
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario con el documento de identidad: " + numeroDocumento);
        }

        return optUser.get();
    }
}
