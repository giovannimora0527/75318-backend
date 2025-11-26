package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.RecuperarPasswordAuditoria;
import com.uniminuto.clinica.model.RecuperarPasswordRequest;
import com.uniminuto.clinica.model.RespuestaRs;

import java.util.List;

public interface RecuperarPasswordService {

    RespuestaRs recuperarPassword(RecuperarPasswordRequest request);

    List<RecuperarPasswordAuditoria> listarTodosLosRegistros();
}