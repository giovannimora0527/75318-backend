package com.uniminuto.clinica.service.impl;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.entity.Cita;

import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetaServiceImpl implements RecetaService {

    private final RecetaRepository RecetaRepository;

    public RecetaServiceImpl(RecetaRepository RecetaRepository) {
        this.RecetaRepository = RecetaRepository;
    }

    @Override
    public Receta guardarReceta(Receta Receta) {
        return RecetaRepository.save(Receta);
    }

    @Override
    public List<Receta> obtenerTodas() {
        return RecetaRepository.findAll();
    }

    @Override
    public Receta obtenerPorId(Long id) {
        return RecetaRepository.findById(id).orElse(null);
    }
    
    @Override
    public List<Receta> listarRecetaPorFechaCreacionDesc() {
        return RecetaRepository.findAllByOrderByFechaCreacionDesc();
}

}
