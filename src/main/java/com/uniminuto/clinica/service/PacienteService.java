package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.entity.Usuario;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface PacienteService {
    List<Paciente> listarPacientes();
    

    Usuario buscarUsuarioPorNumeroDocumento(String numeroDocumento) throws BadRequestException;
}


