package com.uniminuto.clinica.service;


import com.uniminuto.clinica.entity.Historia;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.HistoriaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

import java.util.List;


public interface HistoriaService {

    List<Historia> listarTodasLasHistorias();

    RespuestaRs guardarHistoria(HistoriaRq historiaRq) throws BadRequestException;

    RespuestaRs actualizarHistoria(HistoriaRq historiaRq) throws BadRequestException;

    RespuestaRs eliminarHistoria(Integer idHistoria) throws BadRequestException;

}
