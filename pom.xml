package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<Paciente> listarPacientes() {
        return this.pacienteRepository.findAll();
    }
    @Override
    public Usuario buscarUsuarioPorNumeroDocumento(String numeroDocumento) throws BadRequestException {
        return this.pacienteRepository.findByNumeroDocumento(numeroDocumento)
                .map(Paciente::getUsuario)
                .orElseThrow(() -> new BadRequestException(
                        "Usuario no encontrado con este numero de documento: " + numeroDocumento));
    }
    
}
