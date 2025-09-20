package com.uniminuto.clinica.service;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface RecetaService {

    RespuestaRs crearReceta (RecetaRq recetaRq) throws BadRequestException;
}
