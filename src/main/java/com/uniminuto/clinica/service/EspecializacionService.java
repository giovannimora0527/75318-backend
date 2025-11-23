package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface EspecializacionService {
    List<Especializacion> listarTodo();
    
    Especializacion buscarEspecializacionPorCod(String codigo) throws BadRequestException;
    List<Especializacion> listarOrdenadoPorCodigoEspecializacion(boolean ascendente);
    Especializacion GuardarEspecializacion(Especializacion especializacion) throws BadRequestException;

    public void eliminarEspecializacion(Long id);
}
