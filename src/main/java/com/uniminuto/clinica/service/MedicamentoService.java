package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.MedicamentoRs;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

public interface MedicamentoService {
    List<MedicamentoRs> listarMedicamentos();
    List<MedicamentoRs> listarMedicamentosRecientes();
    RespuestaRs guardarMedicamento(MedicamentoRq medicamentoRq) throws BadRequestException;
}