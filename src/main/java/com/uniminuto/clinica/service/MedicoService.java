package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


/**
 *
 * @author lmora
 */
public interface MedicoService {

    List<Medico> listarMedicos(); 
    
    List<Medico> buscarPorEspecialidad(String codigo);
    
    RespuestaRs guardarMedico(Medico medico);
    
    RespuestaRs actualizarMedico(Long id, Medico medico);

}
