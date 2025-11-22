package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.List;

import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.EspecializacionRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface EspecializacionService {
    List<Especializacion> listarTodo();
    
    Especializacion buscarEspecializacionPorCod(String codigo) 
            throws BadRequestException;

    RespuestaRs guardarEspecializacion(EspecializacionRq especializacionRq) throws BadRequestException;

    RespuestaRs actualizarEspecializacion(EspecializacionRq especializacionRq) throws BadRequestException;

    RespuestaRs eliminarEspecializacion(Integer idEspecializacion) throws BadRequestException;

}
