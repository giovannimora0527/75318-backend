package com.uniminuto.clinica.service.impl;


import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


/**
 *
 * @author jartunduaga
 */
@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;


    @Override
    public List<Paciente> listarLosPacientes() {
        return this.pacienteRepository.findAll();
    }

    @Override
    public List<Paciente> findByNumeroDocumento(String numeroDocumento) {
        return List.of();
    }

    @Override
    public Paciente encontrarPorDocumento(String numeroDocumento)
            throws BadRequestException {
        Optional<Paciente> optUser = this.pacienteRepository
                .findByNumeroDocumento(numeroDocumento);
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el paciente");
        }

        return optUser.get();
    }

}
