package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.HistoriaMedica;
import com.uniminuto.clinica.repository.HistoriaMedicaRepository;
import com.uniminuto.clinica.service.HistoriaMedicaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriaMedicaServiceImpl implements HistoriaMedicaService {

    private final HistoriaMedicaRepository HistoriaMedicaRepository;

    public HistoriaMedicaServiceImpl(HistoriaMedicaRepository HistoriaMedicaRepository) {
        this.HistoriaMedicaRepository = HistoriaMedicaRepository;
    }

    @Override
    public HistoriaMedica guardarHistoriaMedica(HistoriaMedica HistoriaMedica) {
        return HistoriaMedicaRepository.save(HistoriaMedica);
    }

    @Override
    public List<HistoriaMedica> obtenerTodas() {
        return HistoriaMedicaRepository.findAll();
    }

    @Override
    public HistoriaMedica obtenerPorId(Long id) {
        return HistoriaMedicaRepository.findById(id).orElse(null);
    }
    
    @Override
    public List<HistoriaMedica> listarHistoriaMedicaPorFechaCreacionDesc() {
    return HistoriaMedicaRepository.findAllByOrderByFechaCreacionDesc();
}

}
