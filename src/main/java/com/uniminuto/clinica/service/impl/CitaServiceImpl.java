package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;

    public CitaServiceImpl(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    public Cita guardarCita(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> obtenerTodas() {
        return citaRepository.findAll();
    }

    @Override
    public Cita obtenerPorId(Long id) {
        return citaRepository.findById(id).orElse(null);
    }
    
    @Override
    public List<Cita> listarCitasPorFechaHoraDesc() {
    return citaRepository.findAllByOrderByFechaHoraDesc();
}

}
