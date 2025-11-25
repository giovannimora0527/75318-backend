package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.List;

public interface EspecializacionService {
    
    List<Especializacion> listarTodo();
    
    Especializacion buscarEspecializacionPorCod(String codigo);
    
    Especializacion actualizar(Long id, Especializacion especializacion);
    
    Especializacion crear(Especializacion especializacion);
}
