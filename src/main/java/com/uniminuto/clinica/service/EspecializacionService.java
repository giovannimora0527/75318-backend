package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.model.EspecializacionRq;
import com.uniminuto.clinica.model.EspecializacionRs;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

public interface EspecializacionService {
    List<EspecializacionRs> listarEspecializaciones();
    EspecializacionRs buscarPorCodigo(String codigo);
    RespuestaRs guardarEspecializacion(EspecializacionRq request) throws BadRequestException;
    RespuestaRs actualizarEspecializacion(EspecializacionRq request) throws BadRequestException;
    Especializacion buscarEspecializacionPorCod(String codigo) throws BadRequestException;

}
