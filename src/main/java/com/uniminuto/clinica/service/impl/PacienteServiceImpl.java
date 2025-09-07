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
 * @author 1079977_JavierCuervo
 */
@Service
public abstract class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Override
    public List<Paciente> listarTodosLosPacientes() {
        return this.pacienteRepository.findAll();
    }

    @Override
    public Paciente encontrarPorNumeroDocumento(String numero_documento)
            throws BadRequestException {
        Optional<Paciente> optDocument = this.pacienteRepository
                .findByNumeroDocumento(numero_documento);
        if (!optDocument.isPresent()) {
            throw new BadRequestException("No existe el paciente");
        }
        return optDocument.get();
    }
}
