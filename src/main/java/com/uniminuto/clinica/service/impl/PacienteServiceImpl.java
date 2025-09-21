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
 * @author JAVIER-CUERVO
 */
@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;
<<<<<<< HEAD

=======
    
>>>>>>> 6ddb292738158152aa065f4d15449b4ce3a7c0c8
    /**
     *
     * @return
     */
    @Override
    public List<Paciente> listarTodosLosPacientes() {
        return this.pacienteRepository.findAll();
    }

    /**
     *
     * @param numero_documento
     * @return
     * @throws BadRequestException
     */
<<<<<<< HEAD
    
=======
    @Override
>>>>>>> 6ddb292738158152aa065f4d15449b4ce3a7c0c8
    public Paciente encontrarPorNumeroDocumento(String numero_documento)
            throws BadRequestException {
        Optional<Paciente> optDocument = this.pacienteRepository
                .findByNumeroDocumento(numero_documento);
        if (!optDocument.isPresent()) {
            throw new BadRequestException("No existe el paciente");
        }
        return optDocument.get();
    }

    public List<Paciente> listarPorEdadDescendente() {
        return this.pacienteRepository.findAllByFechaNacimientoDesc();
    }
}
