package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

public interface RecetaService {

    RespuestaRs guardarReceta(RecetaRq recetaNueva) throws BadRequestException;
}