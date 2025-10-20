package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<Paciente> encontrarTodosLosPacientes() {
        return this.pacienteRepository.findAll();
    }

    @Override
    public Paciente buscarPacientePorDocumento(String documento) throws BadRequestException {
        Optional<Paciente> optPaciente = this.pacienteRepository.findByNumeroDocumento(documento);
        if (!optPaciente.isPresent()) {
            throw new BadRequestException("No se encuentra el paciente con documento: " + documento);
        }
        return optPaciente.get();
    }

    @Override
    public List<Paciente> listarOrdenadoPorFechaNacimiento(boolean ascendente) {
        if (ascendente) {
            return this.pacienteRepository.findAllByOrderByFechaNacimientoAsc();
        } else {
            return this.pacienteRepository.findAllByOrderByFechaNacimientoDesc();
        }
    }

   
    @Override
    public Paciente guardarOActualizarPaciente(Paciente paciente) throws BadRequestException {
        if (paciente.getId() == null) {
            
            Optional<Paciente> existente = pacienteRepository.findByNumeroDocumento(paciente.getNumeroDocumento());
            if (existente.isPresent()) {
                throw new BadRequestException("Ya existe un paciente con el número de documento: " + paciente.getNumeroDocumento());
            }
        } else {
           
            Optional<Paciente> pacienteBD = pacienteRepository.findById(paciente.getId());
            if (!pacienteBD.isPresent()) {
                 throw new BadRequestException("No se encuentra el paciente con ID: " + paciente.getId() + " para actualizar.");
            }

        
            Optional<Paciente> duplicado = pacienteRepository.findByNumeroDocumentoAndIdNot(paciente.getNumeroDocumento(), paciente.getId());
            if (duplicado.isPresent()) {
                throw new BadRequestException("El número de documento: " + paciente.getNumeroDocumento() + " ya está asignado a otro paciente.");
            }
        }

       
        return pacienteRepository.save(paciente);
    }

}