package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.HistoriaMedica;
import com.uniminuto.clinica.entity.HistoriaMedica;
import java.util.List;

public interface HistoriaMedicaService {

    HistoriaMedica guardarHistoriaMedica(HistoriaMedica HistoriaMedica);
    List<HistoriaMedica> obtenerTodas();
    HistoriaMedica obtenerPorId(Long id);
    List<HistoriaMedica> listarHistoriaMedicaPorFechaCreacionDesc();
}
